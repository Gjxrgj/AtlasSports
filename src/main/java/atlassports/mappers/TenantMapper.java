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

    @Mapping(source = "userId", target = "user.id")
    Tenant toEntity(UpsertTenantDto dto);

    @Mapping(source = "userId", target = "user.id")
    Tenant toEntity(UpsertTenantDto dto, @MappingTarget Tenant tenant);
}
