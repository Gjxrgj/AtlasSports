package atlassports.mappers;

import atlassports.model.Tenant;
import atlassports.model.dto.TenantDto;
import atlassports.model.dto.UpsertTenantDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TenantMapper {

    @Mapping(source = "user.id", target = "userId")
    TenantDto toDto(Tenant tenant);

    List<TenantDto> toDto(List<Tenant> tenants);

    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    Tenant toEntity(UpsertTenantDto dto);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "id", ignore = true)
    Tenant toEntity(UpsertTenantDto dto, @MappingTarget Tenant tenant);
}
