package atlassports.model.dto;

import atlassports.enums.EntityStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class VenueDto {
    private Long id;
    private Long tenantId;
    private String name;
    private String address;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String country;
    private String city;
    private Boolean hasParking;
    private Boolean isIndoor;
    private String phoneNumber;
    private String email;
    private String description;
    private OffsetDateTime createdAt;
    private OffsetDateTime modifiedAt;
    private EntityStatus status;
    private Boolean deleted;
}
