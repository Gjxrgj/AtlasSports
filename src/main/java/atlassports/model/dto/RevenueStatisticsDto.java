package atlassports.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class RevenueStatisticsDto {
    private BigDecimal allTimeTotalRevenue;
    private BigDecimal previousYear;
    private BigDecimal previousMonth;
    private Map<Integer, BigDecimal> quarterly;
    private List<TimeSeriesPointDto> dailyLast30Days;
    private List<TimeSeriesPointDto> weeklyLast12Weeks;
    private List<TimeSeriesPointDto> monthlyLast12Months;
}
