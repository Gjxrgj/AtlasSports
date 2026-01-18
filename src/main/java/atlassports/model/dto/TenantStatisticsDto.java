package atlassports.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class TenantStatisticsDto {
    private RevenueStatisticsDto revenue;
    private BookingStatisticsDto bookings;
    private List<DistributionItemDto> previousYearRevenueByResource;
    private List<DistributionItemDto> previousYearRevenueByVenue;
    private List<DistributionItemDto> previousYearOccupancyByVenue;
}
