package atlassports.service;

import atlassports.model.dto.UpsertWorkingHoursDto;
import atlassports.model.dto.WorkingHoursDto;

import java.util.List;

public interface WorkingHoursService {
    List<WorkingHoursDto> createWorkingHours(Long resourceId, List<UpsertWorkingHoursDto> workingHoursDtos);
}
