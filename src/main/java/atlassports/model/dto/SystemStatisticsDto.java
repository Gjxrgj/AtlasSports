package atlassports.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class SystemStatisticsDto {
    private RevenueStatisticsDto revenue;
    private BookingStatisticsDto bookings;
    private List<DistributionItemDto> previousYearRevenueByTenant;
    private List<DistributionItemDto> previousYearBookingsByTenant;
    private List<DistributionItemDto> previousYearOccupancyByTenant;
}
