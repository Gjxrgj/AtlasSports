package atlassports.mappers;

import atlassports.model.dto.UpsertWorkingHoursDto;
import atlassports.model.dto.WorkingHoursDto;
import org.mapstruct.Mapper;

import atlassports.model.WorkingHours;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkingHoursMapper {

    @Mapping(source = "resource.id", target = "resourceId")
    WorkingHoursDto toDto(WorkingHours workingHours);

    List<WorkingHoursDto> toDto(List<WorkingHours> workingHoursList);

    WorkingHours toEntity(UpsertWorkingHoursDto dto);

    List<WorkingHours> toEntity(List<UpsertWorkingHoursDto> workingHoursDtos);

    WorkingHours toEntity(UpsertWorkingHoursDto dto, @MappingTarget WorkingHours workingHours);
}
