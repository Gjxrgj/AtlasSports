package atlassports.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TenantDto {
    private Long id;
    private Long userId;
    private String name;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime status;
    private Boolean deleted;
}
