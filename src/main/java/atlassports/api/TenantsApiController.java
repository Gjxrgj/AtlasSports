package atlassports.api;

import atlassports.enums.EntityStatus;
import atlassports.model.dto.TenantDto;
import atlassports.model.dto.UpsertTenantDto;
import atlassports.service.TenantService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.processing.Generated;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-24T16:37:25.216838800+01:00[Europe/Skopje]", comments = "Generator version: 7.18.0")
@Controller
@RequestMapping("${openapi.atlasSports.base-path:/api/tenants}")
public class TenantsApiController implements TenantsApi {

    private final NativeWebRequest request;
    private final TenantService tenantService;

    @Autowired
    public TenantsApiController(NativeWebRequest request, TenantService tenantService) {
        this.request = request;
        this.tenantService = tenantService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    @PostMapping
    public ResponseEntity<TenantDto> tenantsPost(@RequestBody UpsertTenantDto body) {
        return ResponseEntity.ok(tenantService.createTenant(body));
    }

        @Override
        @GetMapping
        public ResponseEntity<Page<TenantDto>> tenantsGet(@ParameterObject Pageable pageable, @RequestParam(required = false) String searchQuery) {
            return ResponseEntity.ok(tenantService.getTenants(pageable, searchQuery));
        }

    @Override
    @GetMapping("/{tenantId}")
    public ResponseEntity<TenantDto> tenantsTenantIdGet(@PathVariable Long tenantId) {
        return ResponseEntity.ok(tenantService.getTenant(tenantId));
    }

    @Override
    @PatchMapping("/{tenantId}")
    public ResponseEntity<TenantDto> tenantsTenantIdPatch(@PathVariable Long tenantId, @RequestBody UpsertTenantDto body) {
        return ResponseEntity.ok(tenantService.updateTenant(tenantId, body));
    }

    @Override
    @PatchMapping("/{tenantId}/status")
    public ResponseEntity<TenantDto> tenantsTenantIdStatusPatch(@PathVariable Long tenantId, @RequestParam EntityStatus status) {
        return ResponseEntity.ok(tenantService.changeTenantStatus(tenantId, status));
    }
}
