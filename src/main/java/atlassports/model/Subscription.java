package atlassports.model;

import atlassports.enums.BillingCycle;
import atlassports.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Entity
@Data
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Tenant tenant;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal price;
    private Currency currency;
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;
    @Enumerated(EnumType.STRING)
    private BillingCycle billingCycle;
    private String stripeCustomerId;
    private String stripeSubscriptionId;
    private Boolean autoRenewal;
    private LocalDateTime canceledAt;
}
