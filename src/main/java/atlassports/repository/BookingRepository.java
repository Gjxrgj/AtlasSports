package atlassports.repository;

import atlassports.enums.BookingStatus;
import atlassports.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findAllByUser_IdAndStatus(Pageable pageable, Long userId, BookingStatus bookingStatus);

    Page<Booking> findAlLByResource_IdIn(Pageable pageable, Set<Long> resourceIds);
    List<Booking> findAllByResource_IdInAndStatus(Set<Long> resourceIds, BookingStatus bookingStatus);

    List<Booking> findAllByResource_IdInAndStartTimeGreaterThanEqualAndEndTimeLessThanEqualAndStatus(
            Set<Long> resourceIds,
            LocalDateTime startDate,
            LocalDateTime endDate,
            BookingStatus bookingStatus
    );

    List<Booking> findAllByStartTimeGreaterThanEqualAndEndTimeLessThanEqualAndStatus(LocalDateTime startDate, LocalDateTime endDate, BookingStatus bookingStatus);
    @Query("""
    SELECT COUNT(b)
    FROM Booking b
    WHERE b.startTime IS NOT NULL
      AND b.endTime IS NOT NULL
      AND b.startTime < b.endTime
      AND b.status = atlassports.enums.BookingStatus.COMPLETED
""")
    long countAllTimeValidCompletedBookings();

}
