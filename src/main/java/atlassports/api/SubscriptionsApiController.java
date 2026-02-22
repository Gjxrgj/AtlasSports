package atlassports.api;

import atlassports.enums.SubscriptionPlanEnum;
import atlassports.model.dto.CheckoutSessionResponse;
import atlassports.model.dto.SubscriptionDto;
import atlassports.service.SubscriptionService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.processing.Generated;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-24T16:37:25.216838800+01:00[Europe/Skopje]", comments = "Generator version: 7.18.0")
@Controller
@RequestMapping("${openapi.atlasSports.base-path:/api/subscriptions}")
public class SubscriptionsApiController implements SubscriptionsApi {

    private final NativeWebRequest request;
    private final SubscriptionService subscriptionService;
    @Value("${stripe.webhook-secret}")
    private String endpointSecret;

    @Autowired
    public SubscriptionsApiController(NativeWebRequest request, SubscriptionService subscriptionService) {
        this.request = request;
        this.subscriptionService = subscriptionService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Page<SubscriptionDto>> subscriptionsGet(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(subscriptionService.getSubscriptions(pageable));
    }

    @Override
    @GetMapping("/{subscriptionId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TENANT')")
    public ResponseEntity<SubscriptionDto> subscriptionsSubscriptionIdGet(@PathVariable Long subscriptionId) {
        return ResponseEntity.ok(subscriptionService.getSubscription(subscriptionId));
    }

    @Override
    @GetMapping("/tenants/{tenantId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TENANT')")
    public ResponseEntity<Page<SubscriptionDto>> subscriptionsTenantsTenantIdGet(@ParameterObject Pageable pageable, @PathVariable Long tenantId) {
        return ResponseEntity.ok(subscriptionService.getTenantsSubscriptions(pageable, tenantId));
    }

    @PostMapping("/checkout")
    @PreAuthorize("hasAuthority('TENANT')")
    public ResponseEntity<CheckoutSessionResponse> createCheckoutSession(@RequestParam("planCode") SubscriptionPlanEnum planCode) {
        return ResponseEntity.ok(subscriptionService.createCheckoutSession(planCode));
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                      @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

            subscriptionService.handleStripeEvent(event);

            return ResponseEntity.ok("Received");
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Webhook error");
        }
    }
}
