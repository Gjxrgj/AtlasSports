package atlassports.repository;

import atlassports.model.WorkingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Long> {
    List<WorkingHours> findAllByResource_IdIn(Set<Long> resourceIds);
    List<WorkingHours> findAllByResource_Id(Long resourceId);
}
