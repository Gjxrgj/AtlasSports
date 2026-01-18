package atlassports.mappers;

import atlassports.model.Role;
import atlassports.model.dto.RoleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toDto(Role role);
}
