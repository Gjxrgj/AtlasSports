package atlassports.service;

import atlassports.model.dto.SystemStatisticsDto;
import atlassports.model.dto.TenantStatisticsDto;
import org.springframework.http.ResponseEntity;

public interface StatisticsService {
    SystemStatisticsDto getSystemService();

    TenantStatisticsDto getTenantsStatistics(Long tenantId);
}
