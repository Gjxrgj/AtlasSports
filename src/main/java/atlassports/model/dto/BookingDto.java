package atlassports.model.dto;

import lombok.Data;
import atlassports.enums.BookingStatus;
import java.time.LocalDateTime;

@Data
public class BookingDto {
    private Long id;
    private Long userId;
    private Long resourceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BookingStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
