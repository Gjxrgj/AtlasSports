package atlassports.api;

import atlassports.model.dto.BookingDto;
import atlassports.model.dto.UpsertVenueDto;
import atlassports.model.dto.VenueDto;
import atlassports.model.openapi.VenuesGet200Response;
import atlassports.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-24T16:37:25.216838800+01:00[Europe/Skopje]", comments = "Generator version: 7.18.0")
@Controller
@RequestMapping("${openapi.atlasSports.base-path:/api/venues}")
public class VenuesApiController implements VenuesApi {

    private final NativeWebRequest request;
    private final VenueService venueService;

    @Autowired
    public VenuesApiController(NativeWebRequest request, VenueService venueService) {
        this.request = request;
        this.venueService = venueService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Page<VenueDto>> venuesGet(Pageable pageable, String city, String type, BigDecimal minPrice, BigDecimal maxPrice, Boolean hasParking) {
        return ResponseEntity.ok(venueService.getVenues(pageable, ));
    }

    @GetMapping
    public ResponseEntity<Page<BookingDto>> bookingsGet(Pageable page) {
        return ResponseEntity.ok(bookingService.getBookings(page));
    }


    @Override
    @PostMapping
    public ResponseEntity<VenueDto> venuesPost(UpsertVenueDto body) {
        return ResponseEntity.ok(venueService.createVenue(body));
    }

    @Override
    @GetMapping("/{venueId}")
    public ResponseEntity<VenueDto> venuesVenueIdGet(@PathVariable Long venueId) {
        return ResponseEntity.ok(venueService.getVenue(venueId));
    }

    @Override
    @PatchMapping("/{venueId}")
    public ResponseEntity<VenueDto> venuesVenueIdPatch(@PathVariable Long venueId, UpsertVenueDto body) {
        return ResponseEntity.ok(venueService.updateVenue(venueId, body));
    }
}
