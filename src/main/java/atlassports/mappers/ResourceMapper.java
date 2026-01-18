package atlassports.mappers;

import atlassports.model.Resource;
import atlassports.model.dto.ResourceDto;
import atlassports.model.dto.UpsertResourceDto;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResourceMapper {

    @Mapping(source = "venueId", target = "venue.id")
    Resource toEntity(UpsertResourceDto dto);

    @Mapping(source = "venue.id", target = "venueId")
    ResourceDto toDto(Resource resource);

    List<ResourceDto> toDto(List<Resource> resources);

    @Mapping(source = "venueId", target = "venue.id")
    Resource toEntity(UpsertResourceDto dto, @MappingTarget Resource resource);
}
