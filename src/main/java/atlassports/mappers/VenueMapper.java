package atlassports.mappers;

import atlassports.model.Venue;
import atlassports.model.dto.UpsertVenueDto;
import atlassports.model.dto.VenueDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VenueMapper {

    @Mapping(source = "tenant.id", target = "tenantId")
    VenueDto toDto(Venue venue);

    List<VenueDto> toDto(List<Venue> venues);

    @Mapping(target = "tenant", ignore = true)
    Venue toEntity(UpsertVenueDto dto);

    @Mapping(target = "tenant", ignore = true)
    Venue toEntity(UpsertVenueDto dto, @MappingTarget Venue venue);

}
