package atlassports.mappers;

import atlassports.model.ScheduleOverride;
import atlassports.model.dto.ScheduleOverrideDto;
import atlassports.model.dto.UpsertScheduleOverrideDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleOverrideMapper {
    ScheduleOverrideDto toDto(ScheduleOverride scheduleOverride);

    List<ScheduleOverrideDto> toDto(List<ScheduleOverride> scheduleOverrides);

    ScheduleOverride toEntity(UpsertScheduleOverrideDto scheduleOverrideDto);
}
