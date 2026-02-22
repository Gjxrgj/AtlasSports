package atlassports.service.impl;

import atlassports.enums.SubscriptionPlanEnum;
import atlassports.enums.SubscriptionStatus;
import atlassports.mappers.SubscriptionMapper;
import atlassports.model.Subscription;
import atlassports.model.SubscriptionPlan;
import atlassports.model.Tenant;
import atlassports.model.dto.CheckoutSessionResponse;
import atlassports.model.dto.SubscriptionDto;
import atlassports.repository.SubscriptionPlanRepository;
import atlassports.repository.SubscriptionRepository;
import atlassports.repository.TenantRepository;
import atlassports.service.SubscriptionService;
import atlassports.service.TenantService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.Invoice;
import com.stripe.model.checkout.Session;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final TenantRepository tenantRepository;
    private final TenantService tenantService;
    private final SubscriptionPlanRepository planRepository;

    @Value("${frontend.base-url}")
    private String frontendBaseUrl;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, SubscriptionMapper subscriptionMapper, TenantRepository tenantRepository, TenantService tenantService, SubscriptionPlanRepository planRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionMapper = subscriptionMapper;
        this.tenantRepository = tenantRepository;
        this.tenantService = tenantService;
        this.planRepository = planRepository;
    }

    @Override
    public Page<SubscriptionDto> getSubscriptions(Pageable pageable) {
        return subscriptionRepository.findAll(pageable)
                .map(subscriptionMapper::toDto);
    }

    @Override
    public SubscriptionDto getSubscription(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new NoSuchElementException("Subscription with id " + subscriptionId + " doesn't exist."));
        return subscriptionMapper.toDto(subscription);
    }

    @Override
    public Page<SubscriptionDto> getTenantsSubscriptions(Pageable pageable, Long tenantId) {
        return subscriptionRepository.findAllByTenantId(pageable, tenantId)
                .map(subscriptionMapper::toDto);
    }

    @Override
    @Transactional
    public CheckoutSessionResponse createCheckoutSession(SubscriptionPlanEnum planCode) {

        Tenant tenant = tenantService.getCurrentTenant();

        Optional<Subscription> currentSubOpt = subscriptionRepository
                .findByTenantIdAndStatus(tenant.getId(), SubscriptionStatus.ACTIVE);

        if (currentSubOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tenant already has an active subscription");
        }

        SubscriptionPlan plan = planRepository.findByCodeAndActiveTrue(planCode)
                .orElseThrow(() -> new RuntimeException("Invalid or inactive plan"));

        try {
            if (tenant.getStripeCustomerId() == null) {
                Customer customer = createStripeCustomer(tenant);
                tenant.setStripeCustomerId(customer.getId());
                tenantRepository.save(tenant);
            }

            Session session = createStripeCheckoutSession(tenant, plan);

            CheckoutSessionResponse response = new CheckoutSessionResponse();
            response.setSessionId(session.getId());
            response.setCheckoutUrl(session.getUrl());

            return response;

        } catch (StripeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Stripe API error: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void handleStripeEvent(Event event) {
        switch (event.getType()) {

            case "checkout.session.completed":
                handleCheckoutSessionCompleted((Session) Objects.requireNonNull(event.getDataObjectDeserializer().getObject().orElse(null)));
                break;

            case "customer.subscription.created":
            case "invoice.payment_succeeded":
                handleSubscriptionCreatedOrPaymentSucceeded((com.stripe.model.Subscription) Objects.requireNonNull(event.getDataObjectDeserializer().getObject().orElse(null)));
                break;

            case "invoice.payment_failed":
                handlePaymentFailed((Invoice) Objects.requireNonNull(event.getDataObjectDeserializer().getObject().orElse(null)));
                break;

            default:
                break;
        }
    }

    private void handleCheckoutSessionCompleted(Session session) {
        System.out.println("Checkout session completed: " + session.getId());
    }

    private void handleSubscriptionCreatedOrPaymentSucceeded(com.stripe.model.Subscription stripeSubscription) {
        String stripeCustomerId = stripeSubscription.getCustomer();
        Tenant tenant = tenantRepository.findByStripeCustomerId(stripeCustomerId)
                .orElseThrow(() -> new RuntimeException("Tenant not found for Stripe customer"));

        String stripePriceId = stripeSubscription.getItems().getData().get(0).getPrice().getId();
        SubscriptionPlan plan = planRepository.findByStripePriceId(stripePriceId)
                .orElseThrow(() -> new RuntimeException("Subscription plan not found"));

        Subscription subscription = subscriptionRepository.findByTenantId(tenant.getId())
                .orElse(new Subscription());

        subscription.setTenant(tenant);
        subscription.setPlan(plan);
        subscription.setStripeSubscriptionId(stripeSubscription.getId());
        subscription.setStatus(stripeSubscription.getStatus().equals("active") ? SubscriptionStatus.ACTIVE : SubscriptionStatus.INCOMPLETE);
        subscriptionRepository.save(subscription);
    }

    private void handlePaymentFailed(Invoice invoice) {
        String stripeCustomerId = invoice.getCustomer();
        Tenant tenant = tenantRepository.findByStripeCustomerId(stripeCustomerId)
                .orElseThrow(() -> new RuntimeException("Tenant not found for Stripe customer"));

        Subscription subscription = subscriptionRepository.findByTenantId(tenant.getId())
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        subscription.setStatus(SubscriptionStatus.PAST_DUE);
        subscriptionRepository.save(subscription);
    }

    private Customer createStripeCustomer(Tenant tenant) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("name", tenant.getName());
        params.put("email", tenant.getUser().getEmail());
        return Customer.create(params);
    }

    private Session createStripeCheckoutSession(Tenant tenant, SubscriptionPlan plan) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("customer", tenant.getStripeCustomerId());
        params.put("payment_method_types", List.of("card"));
        params.put("mode", "subscription");
        params.put("line_items", List.of(Map.of("price", plan.getStripePriceId(), "quantity", 1)));
        params.put("success_url", frontendBaseUrl + "/billing/success");
        params.put("cancel_url", frontendBaseUrl + "/billing/cancel");

        return Session.create(params);
    }
}
