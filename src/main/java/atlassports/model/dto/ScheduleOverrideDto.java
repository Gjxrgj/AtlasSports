package atlassports.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleOverrideDto {
    private Long id;
    private Long resourceId;
    private LocalDate date;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Boolean closed;
    private String reason;
}
