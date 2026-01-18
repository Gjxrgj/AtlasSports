package atlassports.mappers;

import atlassports.model.VenueImages;
import atlassports.model.dto.UpsertVenueImageDto;
import atlassports.model.dto.VenueImageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VenueImagesMapper {
    @Mapping(source = "venue.id", target = "venueId")
    VenueImageDto toDto(VenueImages venueImage);

    List<VenueImageDto> toDto(List<VenueImages> venueImages);

    @Mapping(source = "venueId", target = "venue.id")
    VenueImages toEntity(UpsertVenueImageDto dto);

    @Mapping(source = "venueId", target = "venue.id")
    VenueImages toEntity(UpsertVenueImageDto dto, @MappingTarget VenueImages venueImage);
}
