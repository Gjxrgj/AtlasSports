package atlassports.service;

import atlassports.model.dto.SystemStatisticsDto;
import atlassports.model.dto.TenantStatisticsDto;

public interface StatisticsService {
    SystemStatisticsDto getSystemStatistics();

    TenantStatisticsDto getTenantsStatistics(Long tenantId);
}
