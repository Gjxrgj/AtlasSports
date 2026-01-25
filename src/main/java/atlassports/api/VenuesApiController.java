package atlassports.api;

import atlassports.model.dto.UpsertVenueDto;
import atlassports.model.dto.VenueDto;
import atlassports.model.dto.VenueFilter;
import atlassports.service.VenueService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.processing.Generated;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-24T16:37:25.216838800+01:00[Europe/Skopje]", comments = "Generator version: 7.18.0")
@Controller
@PreAuthorize("hasAnyAuthority('ADMIN', 'TENANT')")
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
    @GetMapping
    public ResponseEntity<Page<VenueDto>> venuesGet(@ParameterObject Pageable pageable, @ParameterObject VenueFilter venueFilter) {
        return ResponseEntity.ok(venueService.getVenues(pageable, venueFilter));
    }

    @Override
    @PostMapping
    public ResponseEntity<VenueDto> venuesPost(@RequestBody UpsertVenueDto body) {
        return ResponseEntity.ok(venueService.createVenue(body));
    }

    @Override
    @GetMapping("/{venueId}")
    public ResponseEntity<VenueDto> venuesVenueIdGet(@PathVariable Long venueId) {
        return ResponseEntity.ok(venueService.getVenue(venueId));
    }

    @Override
    @PatchMapping("/{venueId}")
    public ResponseEntity<VenueDto> venuesVenueIdPatch(@PathVariable Long venueId, @RequestBody UpsertVenueDto body) {
        return ResponseEntity.ok(venueService.updateVenue(venueId, body));
    }

    @DeleteMapping("/{venueId}")
    public ResponseEntity<Long> deleteVenue(@PathVariable Long venueId) {
        return ResponseEntity.ok(venueService.deleteVenue(venueId));
    }
}
