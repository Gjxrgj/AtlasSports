package atlassports.model.dto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpsertTenantDto {
    private Long userId;
    private String name;
    private String phoneNumber;
    private LocalDateTime status;
    private Boolean deleted;
}
