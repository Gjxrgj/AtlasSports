package atlassports.model.dto;

import atlassports.enums.EntityStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VenueDto {
    private Long id;
    private Long tenantId;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private EntityStatus status;
    private Boolean deleted;
}
