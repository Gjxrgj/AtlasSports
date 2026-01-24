package atlassports.model.dto;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.OffsetDateTime;

@Data
public class WorkingHoursDto {
    private Long id;
    private Long resourceId;
    private DayOfWeek dayOfWeek;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Boolean closed;
    private OffsetDateTime createdAt;
    private OffsetDateTime modifiedAt;
}
