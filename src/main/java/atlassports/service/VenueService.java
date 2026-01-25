package atlassports.service;

import atlassports.model.dto.UpsertVenueDto;
import atlassports.model.dto.VenueDto;
import atlassports.model.dto.VenueFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VenueService {
    Page<VenueDto> getVenues(Pageable pageable, VenueFilter venueFilter);

    VenueDto createVenue(UpsertVenueDto body);

    VenueDto getVenue(Long venueId);

    VenueDto updateVenue(Long venueId, UpsertVenueDto body);

    Long deleteVenue(Long venueId);
}
