package atlassports.service;

import atlassports.enums.EntityStatus;
import atlassports.model.Tenant;
import atlassports.model.dto.TenantDto;
import atlassports.model.dto.UpsertTenantDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TenantService {
    TenantDto createTenant(UpsertTenantDto body);

    Page<TenantDto> getTenants(Pageable pageable, String searchQuery);

    TenantDto getTenant(Long tenantId);

    TenantDto updateTenant(Long tenantId, UpsertTenantDto body);

    TenantDto changeTenantStatus(Long tenantId, EntityStatus status);

    Tenant getCurrentTenant();
}
