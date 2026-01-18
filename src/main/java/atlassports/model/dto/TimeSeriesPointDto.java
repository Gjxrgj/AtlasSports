package atlassports.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TimeSeriesPointDto {
    private LocalDate date;
    private BigDecimal value;
}
