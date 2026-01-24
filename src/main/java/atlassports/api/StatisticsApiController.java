package atlassports.api;

import atlassports.model.dto.SystemStatisticsDto;
import atlassports.model.dto.TenantStatisticsDto;
import atlassports.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.processing.Generated;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-24T16:37:25.216838800+01:00[Europe/Skopje]", comments = "Generator version: 7.18.0")
@Controller
@RequestMapping("${openapi.atlasSports.base-path:/api/statistics}")
public class StatisticsApiController implements StatisticsApi {

    private final NativeWebRequest request;
    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsApiController(NativeWebRequest request, StatisticsService statisticsService) {
        this.request = request;
        this.statisticsService = statisticsService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/system")
    public ResponseEntity<SystemStatisticsDto> statisticsSystemGet() {
        return ResponseEntity.ok(statisticsService.getSystemStatistics());
    }

    @Override
    @PreAuthorize("hasAuthority('TENANT')")
    @GetMapping("/tenants/{tenantId}")
    public ResponseEntity<TenantStatisticsDto> statisticsTenantsTenantIdGet(@PathVariable Long tenantId) {
        return ResponseEntity.ok(statisticsService.getTenantsStatistics(tenantId));
    }
}
