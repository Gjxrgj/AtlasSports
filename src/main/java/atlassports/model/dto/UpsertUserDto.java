package atlassports.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpsertUserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Long> roleIds;
    private Boolean deleted;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
}
