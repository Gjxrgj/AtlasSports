package atlassports.model.dto;

import atlassports.enums.EntityStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ResourceDto {
    private Long id;
    private Long venueId;
    private String name;
    private BigDecimal pricePerHourInEuro;
    private EntityStatus status;
    private Integer capacity;
    private Boolean defaultResource;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
