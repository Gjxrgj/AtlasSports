package atlassports.service;

import atlassports.enums.SubscriptionPlanEnum;
import atlassports.model.dto.CheckoutSessionResponse;
import atlassports.model.dto.SubscriptionDto;
import com.stripe.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubscriptionService {
    Page<SubscriptionDto> getSubscriptions(Pageable pageable);

    SubscriptionDto getSubscription(Long subscriptionId);

    Page<SubscriptionDto> getTenantsSubscriptions(Pageable pageable, Long tenantId);

    CheckoutSessionResponse createCheckoutSession(SubscriptionPlanEnum planCode);

    void handleStripeEvent(Event event);
}
