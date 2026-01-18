package atlassports.service;

import atlassports.model.dto.BookingDto;
import atlassports.model.dto.UpsertBookingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    Page<BookingDto> getBookings(Pageable pageable);

    Page<BookingDto> getBookingHistoryForMe(Pageable page);

    Page<BookingDto> getUpcomingBookingsMe(Pageable page);

    BookingDto createBooking(UpsertBookingDto upsertBookingDto);

    Page<BookingDto> getBookingsForTenant(Pageable page, Long tenantId);
}
