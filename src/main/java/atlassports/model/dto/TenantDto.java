package atlassports.model.dto;

import atlassports.enums.EntityStatus;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class TenantDto {
    private Long id;
    private Long userId;
    private String name;
    private String phoneNumber;
    private OffsetDateTime createdAt;
    private OffsetDateTime modifiedAt;
    private EntityStatus status;
    private Boolean deleted;
}
