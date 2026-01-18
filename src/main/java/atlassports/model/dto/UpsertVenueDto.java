package atlassports.model.dto;
import atlassports.enums.EntityStatus;
import lombok.Data;

@Data
public class UpsertVenueDto {
    private Long tenantId;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String description;
    private EntityStatus status;
    private Boolean deleted;
}
