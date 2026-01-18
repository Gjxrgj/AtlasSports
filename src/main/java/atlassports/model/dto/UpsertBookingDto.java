package atlassports.model.dto;

import atlassports.enums.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpsertBookingDto {
    private Long resourceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BookingStatus status;
}
