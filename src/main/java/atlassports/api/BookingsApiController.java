package atlassports.api;


import atlassports.model.dto.BookingDto;
import atlassports.model.dto.UpsertBookingDto;
import atlassports.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.processing.Generated;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-24T16:37:25.216838800+01:00[Europe/Skopje]", comments = "Generator version: 7.18.0")
@RestController
@RequestMapping("${openapi.atlasSports.base-path:/api/bookings}")
public class BookingsApiController implements BookingsApi {

    private final NativeWebRequest request;

    private final BookingService bookingService;

    @Autowired
    public BookingsApiController(NativeWebRequest request, BookingService bookingService) {
        this.request = request;
        this.bookingService = bookingService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<BookingDto>> bookingsGet(Pageable page) {
        return ResponseEntity.ok(bookingService.getBookings(page));
    }

    @Override
    @GetMapping("/me/history")
    public ResponseEntity<Page<BookingDto>> bookingsMeHistoryGet(Pageable page) {
        return ResponseEntity.ok(bookingService.getBookingHistoryForMe(page));
    }

    @Override
    @GetMapping("/me/upcoming")
    public ResponseEntity<Page<BookingDto>> bookingsMeUpcomingGet(Pageable page) {
        return ResponseEntity.ok(bookingService.getUpcomingBookingsMe(page));
    }

    @Override
    @PostMapping
    public ResponseEntity<BookingDto> bookingsPost(@Valid @RequestBody UpsertBookingDto upsertBookingDto) {
        return ResponseEntity.ok(bookingService.createBooking(upsertBookingDto));
    }

    @Override
    @GetMapping("/tenants/{tenantId}")
    public ResponseEntity<Page<BookingDto>> bookingsTenantsTenantIdGet(Pageable page, @PathVariable Long tenantId) {
        return ResponseEntity.ok(bookingService.getBookingsForTenant(page, tenantId));
    }
}
