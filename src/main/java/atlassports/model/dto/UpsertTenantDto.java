package atlassports.model.dto;
import atlassports.enums.EntityStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpsertTenantDto {
    private Long userId;
    private String name;
    private String phoneNumber;
    private EntityStatus status;
    private Boolean deleted;
}
