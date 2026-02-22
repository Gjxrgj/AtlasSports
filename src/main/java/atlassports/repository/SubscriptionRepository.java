package atlassports.repository;

import atlassports.enums.SubscriptionStatus;
import atlassports.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Page<Subscription> findAllByTenantId(Pageable pageable, Long tenantId);

    Optional<Subscription> findByTenantId(Long tenantId);

    Optional<Subscription> findByTenantIdAndStatus(Long tenantId, SubscriptionStatus status);
}
