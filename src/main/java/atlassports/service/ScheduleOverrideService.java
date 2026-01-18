package atlassports.service;

import atlassports.model.dto.ScheduleOverrideDto;
import atlassports.model.dto.UpsertScheduleOverrideDto;

import java.util.List;

public interface ScheduleOverrideService {
    ScheduleOverrideDto createScheduleOverride(UpsertScheduleOverrideDto upsertScheduleOverrideDto);
    List<ScheduleOverrideDto> createMultipleScheduleOverrides(List<UpsertScheduleOverrideDto> upsertScheduleOverrideDtos);
}
