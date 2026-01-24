package atlassports.model.dto;

import atlassports.enums.BillingCycle;
import atlassports.enums.SubscriptionStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Currency;

@Data
public class SubscriptionDto {
    private Long id;
    private Long tenantId;
    private OffsetDateTime createdAt;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal price;
    private Currency currency;
    private SubscriptionStatus status;
    private BillingCycle billingCycle;
    private String stripeCustomerId;
    private String stripeSubscriptionId;
    private Boolean autoRenewal;
    private LocalDateTime canceledAt;
}
