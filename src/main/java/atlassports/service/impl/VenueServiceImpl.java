package atlassports.service.impl;

import atlassports.mappers.VenueMapper;
import atlassports.model.Tenant;
import atlassports.model.Venue;
import atlassports.model.dto.UpsertVenueDto;
import atlassports.model.dto.VenueDto;
import atlassports.model.dto.VenueFilter;
import atlassports.repository.TenantRepository;
import atlassports.repository.VenueRepository;
import atlassports.service.VenueService;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;
    private final VenueMapper venueMapper;
    private final TenantRepository tenantRepository;

    public VenueServiceImpl(VenueRepository venueRepository, VenueMapper venueMapper, TenantRepository tenantRepository) {
        this.venueRepository = venueRepository;
        this.venueMapper = venueMapper;
        this.tenantRepository = tenantRepository;
    }

    @Override
    public Page<VenueDto> getVenues(Pageable pageable, VenueFilter filter) {
        Specification<Venue> spec = buildSpec(filter);
        return venueRepository.findAll(spec, pageable)
                .map(venueMapper::toDto);
    }

    @Override
    public VenueDto createVenue(UpsertVenueDto body) {
        final Long tenantId = body.getTenantId();
        final Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new NoSuchElementException("Tenant with id: " + tenantId + " doesn't exist."));
        Venue venue = venueMapper.toEntity(body);
        venue.setTenant(tenant);

        Venue savedVenue = venueRepository.save(venue);
        return venueMapper.toDto(savedVenue);
    }

    @Override
    public VenueDto getVenue(Long venueId) {
        Venue venue = getVenueOrThrowException(venueId);

        return venueMapper.toDto(venue);
    }

    @Override
    public VenueDto updateVenue(Long venueId, UpsertVenueDto body) {
        Venue venue = getVenueOrThrowException(venueId);

        Venue updatedVenue = venueMapper.toEntity(body, venue);

        Venue savedVenue = venueRepository.save(updatedVenue);
        return venueMapper.toDto(savedVenue);
    }

    @Override
    public Long deleteVenue(Long venueId) {
        Venue venue = getVenueOrThrowException(venueId);
        venueRepository.delete(venue);
        return venueId;
    }

    private Venue getVenueOrThrowException(Long venueId) {
        return venueRepository.findById(venueId)
                .orElseThrow(() -> new NoSuchElementException("Venue with id: " + venueId + " doesn't exist."));
    }

    private Specification<Venue> buildSpec(VenueFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getCity() != null) {
                predicates.add(cb.equal(root.get("city"), filter.getCity()));
            }
            if (filter.getCountry() != null) {
                predicates.add(cb.equal(root.get("country"), filter.getCountry()));
            }
            if (filter.getHasParking() != null) {
                predicates.add(cb.equal(root.get("hasParking"), filter.getHasParking()));
            }
            if (filter.getIsIndoor() != null) {
                predicates.add(cb.equal(root.get("isIndoor"), filter.getIsIndoor()));
            }
            if (filter.getSearchTerm() != null && !filter.getSearchTerm().isBlank()) {
                String term = filter.getSearchTerm().trim().toLowerCase();
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + term + "%"));
            }

            if (filter.getUserLatitude() != null && filter.getUserLongitude() != null && filter.getRadiusKm() != null) {
                BigDecimal userLat = filter.getUserLatitude();
                BigDecimal userLon = filter.getUserLongitude();
                BigDecimal radius = filter.getRadiusKm();

                Expression<BigDecimal> lat = root.get("latitude").as(BigDecimal.class);
                Expression<BigDecimal> lon = root.get("longitude").as(BigDecimal.class);

                Expression<BigDecimal> deltaLon = cb.toBigDecimal(cb.diff(lon, cb.literal(userLon)));

                Expression<BigDecimal> distance = cb.prod(
                        BigDecimal.valueOf(6371),
                        cb.function(
                                "acos",
                                BigDecimal.class,
                                cb.sum(
                                        cb.prod(
                                                cb.prod(
                                                        cb.function("cos", Double.class,
                                                                cb.function("radians", Double.class, cb.literal(userLat))),
                                                        cb.function("cos", Double.class, cb.function("radians", Double.class, lat))
                                                ),
                                                cb.function("cos", Double.class, cb.function("radians", Double.class, deltaLon))
                                        ),
                                        cb.prod(
                                                cb.function("sin", Double.class,
                                                        cb.function("radians", Double.class, cb.literal(userLat))),
                                                cb.function("sin", Double.class, cb.function("radians", Double.class, lat))
                                        )
                                )
                        )
                );

                predicates.add(cb.le(distance, radius));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
