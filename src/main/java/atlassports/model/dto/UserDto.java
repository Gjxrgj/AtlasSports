package atlassports.model.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private OffsetDateTime createdAt;
    private OffsetDateTime modifiedAt;
    private Boolean deleted;
    private List<Long> roleIds;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}
