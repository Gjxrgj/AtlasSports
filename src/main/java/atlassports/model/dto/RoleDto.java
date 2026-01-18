package atlassports.model.dto;

import atlassports.enums.RoleEnum;
import lombok.Data;

@Data
public class RoleDto {
    private Long id;
    private RoleEnum name;
}
