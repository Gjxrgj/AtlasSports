package atlassports.mappers;

import atlassports.model.Role;
import atlassports.model.User;
import atlassports.model.dto.UpsertUserDto;
import atlassports.model.dto.UserDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "roleIds", expression = "java(mapRolesToRoleIds(user.getRoles()))")
    public abstract UserDto toDto(User user);

    public abstract List<UserDto> toDto(List<User> users);

    @Mapping(target = "roles", expression = "java(mapRoleIdsToRoles(dto.getRoleIds()))")
    public abstract User toEntity(UpsertUserDto dto);

    @Mapping(target = "roles", expression = "java(mapRoleIdsToRoles(dto.getRoleIds()))")
    public abstract User toEntity(UpsertUserDto dto, @MappingTarget User user);

    protected List<Role> mapRoleIdsToRoles(List<Long> roleIds) {
        if (roleIds == null) return null;
        return roleIds.stream().map(id -> {
            Role r = new Role();
            r.setId(id);
            return r;
        }).collect(Collectors.toList());
    }

    protected List<Long> mapRolesToRoleIds(List<Role> roles) {
        if (roles == null) return null;
        return roles.stream().map(Role::getId).collect(Collectors.toList());
    }

    @AfterMapping
    protected void encodePassword(UpsertUserDto dto, @MappingTarget User user) {
        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
    }
}
