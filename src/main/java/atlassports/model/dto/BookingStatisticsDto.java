package atlassports.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class BookingStatisticsDto {
    private BigDecimal allTimeTotalBookings;
    private BigDecimal previousMonth;
    private BigDecimal previousYear;
    private Map<Integer, BigDecimal> quarterly;
    private List<TimeSeriesPointDto> dailyLast30Days;
    private List<TimeSeriesPointDto> weeklyLast12Weeks;
    private List<TimeSeriesPointDto> monthlyLast12Months;
}
