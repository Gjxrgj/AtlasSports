package atlassports.repository;

import atlassports.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long>, JpaSpecificationExecutor<Tenant> {
    List<Tenant> findAllByDeletedIsFalse();
    Optional<Tenant> findByUser_Id(Long userId);
    Optional<Tenant> findByStripeCustomerId(String customerId);
}
