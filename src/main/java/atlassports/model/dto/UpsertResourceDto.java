package atlassports.model.dto;

import atlassports.enums.EntityStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UpsertResourceDto {
    private Long venueId;
    private String name;
    private BigDecimal pricePerHourInEuro;
    private EntityStatus status;
    private Integer capacity = 1;
    private Boolean defaultResource = false;
    private List<UpsertWorkingHoursDto> workingHours;
    private List<UpsertScheduleOverrideDto> scheduleOverrideDtos;
}
