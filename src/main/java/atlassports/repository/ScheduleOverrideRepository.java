package atlassports.repository;

import atlassports.model.ScheduleOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ScheduleOverrideRepository extends JpaRepository<ScheduleOverride, Long> {
    List<ScheduleOverride> findAllByResource_IdIn(Set<Long> resourceIds);
}
