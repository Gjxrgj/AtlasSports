package atlassports.service.impl;

import atlassports.enums.TimeSeriesType;
import atlassports.model.*;
import atlassports.model.dto.*;
import atlassports.repository.*;
import atlassports.service.StatisticsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final BookingRepository bookingRepository;
    private final VenueRepository venueRepository;
    private final ResourceRepository resourceRepository;
    private final WorkingHoursRepository workingHoursRepository;
    private final ScheduleOverrideRepository scheduleOverrideRepository;

    public StatisticsServiceImpl(BookingRepository bookingRepository, VenueRepository venueRepository, ResourceRepository resourceRepository, WorkingHoursRepository workingHoursRepository, ScheduleOverrideRepository scheduleOverrideRepository) {
        this.bookingRepository = bookingRepository;
        this.venueRepository = venueRepository;
        this.resourceRepository = resourceRepository;
        this.workingHoursRepository = workingHoursRepository;
        this.scheduleOverrideRepository = scheduleOverrideRepository;
    }

    @Override
    public SystemStatisticsDto getSystemService() {

        return null;
    }

    public static LocalDate getStartOfLastMonth() {
        return LocalDate.now().minusMonths(1).withDayOfMonth(1);
    }

    public static LocalDate getEndOfLastMonth() {
        return LocalDate.now().minusMonths(1)
                .withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth());
    }

    public static LocalDate getFirstDayOfPreviousYear() {
        return LocalDate.now().minusYears(1).withMonth(1).withDayOfMonth(1);
    }

    @Override
    public TenantStatisticsDto getTenantsStatistics(Long tenantId) {
        TenantStatisticsDto tenantStatistics = new TenantStatisticsDto();
        final LocalDateTime oneYearAgo = LocalDateTime.now()
                .minusYears(1)
                .toLocalDate()
                .atStartOfDay();

        List<Venue> venuesForTenant = venueRepository
                .findAllByTenant_Id(tenantId);

        List<Resource> resourcesForVenues = resourceRepository
                .findAllByVenue_IdIn(venuesForTenant
                        .stream()
                        .map(Venue::getId)
                        .collect(Collectors.toSet()));

        List<Booking> last12MonthsBookingsForTenant = bookingRepository
                .findAllByResource_IdInAndCreatedAtIsAfter(
                        resourcesForVenues
                                .stream()
                                .map(Resource::getId)
                                .collect(Collectors.toSet()),
                        oneYearAgo);

        List<Booking> previousYearBookings = bookingRepository
                .findAllByResource_IdInAndCreatedAtIsAfter(
                        resourcesForVenues
                                .stream()
                                .map(Resource::getId)
                                .collect(Collectors.toSet()),
                        oneYearAgo);

        tenantStatistics.setRevenue(getRevenueStatistics(resourcesForVenues, last12MonthsBookingsForTenant, previousYearBookings));
        tenantStatistics.setBookings(getBookingStatistics(last12MonthsBookingsForTenant, previousYearBookings));
        tenantStatistics.setPreviousYearRevenueByResource(getRevenueByResource(resourcesForVenues, previousYearBookings, tenantStatistics.getRevenue().getPreviousYear()));
        tenantStatistics.setPreviousYearRevenueByVenue(getRevenueByVenue(venuesForTenant, resourcesForVenues, tenantStatistics.getPreviousYearRevenueByResource()));
        tenantStatistics.setPreviousYearOccupancyByVenue(getOccupancyByVenue(venuesForTenant, resourcesForVenues, previousYearBookings));

        return tenantStatistics;
    }

    private RevenueStatisticsDto getRevenueStatistics(
            List<Resource> tenantResources,
            List<Booking> last12MonthsBookingsForTenant,
            List<Booking> previousYearBookings
    ) {
        Map<Long, Resource> resourceMap = tenantResources
                .stream()
                .collect(Collectors.toMap(Resource::getId, resource -> resource));

        BigDecimal previousYearRevenue = BigDecimal.ZERO;
        BigDecimal previousMonthRevenue = BigDecimal.ZERO;
        Map<Integer, BigDecimal> quarterly = createTimeSeriesMap(TimeSeriesType.QUARTERLY);
        Map<LocalDate, TimeSeriesPointDto> dailyLast30Days = createTimeSeriesMap(TimeSeriesType.DAILY_30_DAYS);
        Map<LocalDate, TimeSeriesPointDto> weeklyLast12Weeks = createTimeSeriesMap(TimeSeriesType.WEEKLY_12_WEEKS);
        Map<LocalDate, TimeSeriesPointDto> monthlyLast12Months = createTimeSeriesMap(TimeSeriesType.MONTHLY_12_MONTHS);

        for (Booking booking : last12MonthsBookingsForTenant) {
            if (booking.getStartTime() == null || booking.getEndTime() == null) continue;
            if (booking.getResource() == null) continue;

            Resource resource = resourceMap.get(booking.getResource().getId());
            if (resource == null || resource.getPricePerHourInEuro() == null) continue;

            Duration duration = Duration.between(booking.getStartTime(), booking.getEndTime());
            if (duration.isNegative() || duration.isZero()) continue;

            BigDecimal hours = BigDecimal.valueOf(duration.toMinutes())
                    .divide(BigDecimal.valueOf(60), 4, RoundingMode.HALF_UP);

            BigDecimal priceForBooking = hours
                    .multiply(resource.getPricePerHourInEuro())
                    .setScale(2, RoundingMode.HALF_UP);

            final LocalDate bookingCreatedAt = booking.getCreatedAt().toLocalDate();
            final LocalDate bookingCreatedAtFirstDayOfWeek = bookingCreatedAt
                    .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            final LocalDate bookingCreatedAtFirstDayOfMonth = bookingCreatedAt.withDayOfMonth(1);

            if (dailyLast30Days.containsKey(bookingCreatedAt)) {
                final TimeSeriesPointDto timeSeriesPoint = dailyLast30Days.get(bookingCreatedAt);
                final BigDecimal lastValue = timeSeriesPoint.getValue();
                dailyLast30Days.get(bookingCreatedAt).setValue(lastValue.add(priceForBooking));
            }

            if (weeklyLast12Weeks.containsKey(bookingCreatedAtFirstDayOfWeek)) {
                final TimeSeriesPointDto timeSeriesPoint = weeklyLast12Weeks.get(bookingCreatedAtFirstDayOfWeek);
                final BigDecimal lastValue = timeSeriesPoint.getValue();
                weeklyLast12Weeks.get(bookingCreatedAtFirstDayOfWeek).setValue(lastValue.add(priceForBooking));
            }

            if (monthlyLast12Months.containsKey(bookingCreatedAtFirstDayOfMonth)) {
                final TimeSeriesPointDto timeSeriesPoint = monthlyLast12Months.get(bookingCreatedAtFirstDayOfMonth);
                final BigDecimal lastValue = timeSeriesPoint.getValue();
                monthlyLast12Months.get(bookingCreatedAtFirstDayOfMonth).setValue(lastValue.add(priceForBooking));
            }

            if (!bookingCreatedAt.isBefore(getStartOfLastMonth()) && !bookingCreatedAt.isAfter(getEndOfLastMonth())) {
                previousMonthRevenue = previousMonthRevenue.add(priceForBooking);
            }
        }

        for (Booking booking : previousYearBookings) {
            if (booking.getStartTime() == null || booking.getEndTime() == null) continue;
            if (booking.getResource() == null) continue;

            Resource resource = resourceMap.get(booking.getResource().getId());
            if (resource == null || resource.getPricePerHourInEuro() == null) continue;

            Duration duration = Duration.between(booking.getStartTime(), booking.getEndTime());
            if (duration.isNegative() || duration.isZero()) continue;

            BigDecimal hours = BigDecimal.valueOf(duration.toMinutes())
                    .divide(BigDecimal.valueOf(60), 4, RoundingMode.HALF_UP);

            BigDecimal priceForBooking = hours
                    .multiply(resource.getPricePerHourInEuro())
                    .setScale(2, RoundingMode.HALF_UP);

            previousYearRevenue = previousYearRevenue.add(priceForBooking);

            final LocalDate bookingCreatedAt = booking.getCreatedAt().toLocalDate();
            LocalDate firstDayOfPreviousYear = getFirstDayOfPreviousYear();

            if (!bookingCreatedAt.isBefore(firstDayOfPreviousYear) && !bookingCreatedAt.isAfter(firstDayOfPreviousYear.plusMonths(3))) {
                quarterly.put(1, quarterly.get(1).add(priceForBooking));
            } else if (!bookingCreatedAt.isBefore(firstDayOfPreviousYear.plusMonths(3)) && !bookingCreatedAt.isAfter(firstDayOfPreviousYear.plusMonths(6))) {
                quarterly.put(2, quarterly.get(2).add(priceForBooking));
            } else if (!bookingCreatedAt.isBefore(firstDayOfPreviousYear.plusMonths(6)) && !bookingCreatedAt.isAfter(firstDayOfPreviousYear.plusMonths(9))) {
                quarterly.put(3, quarterly.get(3).add(priceForBooking));
            } else if (!bookingCreatedAt.isBefore(firstDayOfPreviousYear.plusMonths(9)) && !bookingCreatedAt.isAfter(firstDayOfPreviousYear.plusMonths(12))) {
                quarterly.put(4, quarterly.get(4).add(priceForBooking));
            }
        }

        RevenueStatisticsDto revenueStatisticsDto = new RevenueStatisticsDto();
        revenueStatisticsDto.setPreviousYear(previousYearRevenue);
        revenueStatisticsDto.setPreviousMonth(previousMonthRevenue);
        revenueStatisticsDto.setQuarterly(quarterly);
        revenueStatisticsDto.setDailyLast30Days(dailyLast30Days
                .values()
                .stream()
                .sorted(Comparator.comparing(TimeSeriesPointDto::getDate))
                .toList());
        revenueStatisticsDto.setWeeklyLast12Weeks(weeklyLast12Weeks
                .values()
                .stream()
                .sorted(Comparator.comparing(TimeSeriesPointDto::getDate))
                .toList());
        revenueStatisticsDto.setMonthlyLast12Months(monthlyLast12Months
                .values()
                .stream()
                .sorted(Comparator.comparing(TimeSeriesPointDto::getDate))
                .toList());

        return revenueStatisticsDto;
    }

    private BookingStatisticsDto getBookingStatistics(
            List<Booking> last12MonthsBookingsForTenant,
            List<Booking> previousYearBookings
    ) {
        List<Booking> lastMonthBookings = last12MonthsBookingsForTenant
                .stream()
                .filter(booking -> {
                    LocalDate bookingDate = booking.getCreatedAt().toLocalDate();
                    return !bookingDate.isBefore(getStartOfLastMonth()) && !bookingDate.isAfter(getEndOfLastMonth());
                })
                .toList();
        final Long numLastMonthBookings = (long) lastMonthBookings.size();
        final Long numLastYearBookings = (long) previousYearBookings.size();

        Map<Integer, BigDecimal> quarterly = createTimeSeriesMap(TimeSeriesType.QUARTERLY);
        Map<LocalDate, TimeSeriesPointDto> dailyLast30Days = createTimeSeriesMap(TimeSeriesType.DAILY_30_DAYS);
        Map<LocalDate, TimeSeriesPointDto> weeklyLast12Weeks = createTimeSeriesMap(TimeSeriesType.WEEKLY_12_WEEKS);
        Map<LocalDate, TimeSeriesPointDto> monthlyLast12Months = createTimeSeriesMap(TimeSeriesType.MONTHLY_12_MONTHS);

        for (Booking booking : last12MonthsBookingsForTenant) {
            if (booking.getStartTime() == null || booking.getEndTime() == null) continue;
            if (booking.getResource() == null) continue;

            Duration duration = Duration.between(booking.getStartTime(), booking.getEndTime());
            if (duration.isNegative() || duration.isZero()) continue;

            final LocalDate bookingCreatedAt = booking.getCreatedAt().toLocalDate();
            final LocalDate bookingCreatedAtFirstDayOfWeek = bookingCreatedAt
                    .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            final LocalDate bookingCreatedAtFirstDayOfMonth = bookingCreatedAt.withDayOfMonth(1);

            if (dailyLast30Days.containsKey(bookingCreatedAt)) {
                final TimeSeriesPointDto timeSeriesPoint = dailyLast30Days.get(bookingCreatedAt);
                final BigDecimal lastValue = timeSeriesPoint.getValue();
                dailyLast30Days.get(bookingCreatedAt).setValue(lastValue.add(BigDecimal.ONE));
            }

            if (weeklyLast12Weeks.containsKey(bookingCreatedAtFirstDayOfWeek)) {
                final TimeSeriesPointDto timeSeriesPoint = weeklyLast12Weeks.get(bookingCreatedAtFirstDayOfWeek);
                final BigDecimal lastValue = timeSeriesPoint.getValue();
                weeklyLast12Weeks.get(bookingCreatedAtFirstDayOfWeek).setValue(lastValue.add(BigDecimal.ONE));
            }

            if (monthlyLast12Months.containsKey(bookingCreatedAtFirstDayOfMonth)) {
                final TimeSeriesPointDto timeSeriesPoint = monthlyLast12Months.get(bookingCreatedAtFirstDayOfMonth);
                final BigDecimal lastValue = timeSeriesPoint.getValue();
                monthlyLast12Months.get(bookingCreatedAtFirstDayOfMonth).setValue(lastValue.add(BigDecimal.ONE));
            }
        }


        for (Booking booking : previousYearBookings) {
            if (booking.getStartTime() == null || booking.getEndTime() == null) continue;
            if (booking.getResource() == null) continue;


            Duration duration = Duration.between(booking.getStartTime(), booking.getEndTime());
            if (duration.isNegative() || duration.isZero()) continue;

            final LocalDate bookingCreatedAt = booking.getCreatedAt().toLocalDate();
            LocalDate firstDayOfPreviousYear = getFirstDayOfPreviousYear();

            if (!bookingCreatedAt.isBefore(firstDayOfPreviousYear) && !bookingCreatedAt.isAfter(firstDayOfPreviousYear.plusMonths(3))) {
                quarterly.put(1, quarterly.get(1).add(BigDecimal.ONE));
            } else if (!bookingCreatedAt.isBefore(firstDayOfPreviousYear.plusMonths(3)) && !bookingCreatedAt.isAfter(firstDayOfPreviousYear.plusMonths(6))) {
                quarterly.put(2, quarterly.get(2).add(BigDecimal.ONE));
            } else if (!bookingCreatedAt.isBefore(firstDayOfPreviousYear.plusMonths(6)) && !bookingCreatedAt.isAfter(firstDayOfPreviousYear.plusMonths(9))) {
                quarterly.put(3, quarterly.get(3).add(BigDecimal.ONE));
            } else if (!bookingCreatedAt.isBefore(firstDayOfPreviousYear.plusMonths(9)) && !bookingCreatedAt.isAfter(firstDayOfPreviousYear.plusMonths(12))) {
                quarterly.put(4, quarterly.get(4).add(BigDecimal.ONE));
            }
        }

        BookingStatisticsDto bookingStatisticsDto = new BookingStatisticsDto();
        bookingStatisticsDto.setLastMonth(numLastMonthBookings);
        bookingStatisticsDto.setLastYear(numLastYearBookings);
        bookingStatisticsDto.setQuarterly(quarterly);
        bookingStatisticsDto.setDailyLast30Days(dailyLast30Days
                .values()
                .stream()
                .sorted(Comparator.comparing(TimeSeriesPointDto::getDate))
                .toList());
        bookingStatisticsDto.setWeeklyLast12Weeks(weeklyLast12Weeks
                .values()
                .stream()
                .sorted(Comparator.comparing(TimeSeriesPointDto::getDate))
                .toList());
        bookingStatisticsDto.setMonthlyLast12Months(monthlyLast12Months
                .values()
                .stream()
                .sorted(Comparator.comparing(TimeSeriesPointDto::getDate))
                .toList());

        return bookingStatisticsDto;
    }

    private List<DistributionItemDto> getRevenueByResource(
            List<Resource> tenantResources,
            List<Booking> previousYearTenantBookings,
            BigDecimal totalRevenuePreviousYear
    ) {
        return tenantResources.stream()
                .map(resource -> {
                    BigDecimal resourceRevenue = previousYearTenantBookings.stream()
                            .filter(b -> b.getResource() != null && b.getResource().getId().equals(resource.getId()))
                            .map(b -> {
                                Duration duration = Duration.between(b.getStartTime(), b.getEndTime());
                                BigDecimal hours = BigDecimal.valueOf(duration.toMinutes())
                                        .divide(BigDecimal.valueOf(60), 4, RoundingMode.HALF_UP);
                                return hours.multiply(resource.getPricePerHourInEuro());
                            })
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal percentage = BigDecimal.ZERO;
                    if (totalRevenuePreviousYear.compareTo(BigDecimal.ZERO) > 0) {
                        percentage = resourceRevenue
                                .divide(totalRevenuePreviousYear, 4, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(100));
                    }

                    DistributionItemDto dto = new DistributionItemDto();
                    dto.setId(resource.getId());
                    dto.setName(resource.getName());
                    dto.setPercentage(percentage);
                    return dto;
                })
                .toList();
    }

    private List<DistributionItemDto> getRevenueByVenue(
            List<Venue> tenantVenues,
            List<Resource> tenantResources,
            List<DistributionItemDto> revenueByResource
    ) {
        Map<Long, Resource> tenantResourcesMap = tenantResources
                .stream()
                .collect(Collectors.toMap(Resource::getId, resource -> resource));

        return tenantVenues.stream()
                .map(venue -> {
                    BigDecimal venuePercentage = revenueByResource.stream()
                            .filter(r -> r.getId() != null && tenantResourcesMap.get(r.getId()).getVenue().getId().equals(venue.getId()))
                            .map(DistributionItemDto::getPercentage)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    DistributionItemDto dto = new DistributionItemDto();
                    dto.setId(venue.getId());
                    dto.setName(venue.getName());
                    dto.setPercentage(venuePercentage)  ;

                    return dto;
                })
                .toList();
    }

    private List<DistributionItemDto> getOccupancyByVenue(
            List<Venue> tenantVenues,
            List<Resource> tenantResources,
            List<Booking> previousYearBookings
    ) {
        LocalDate startDate = LocalDate.now().minusYears(1).withDayOfYear(1);
        LocalDate endDate = LocalDate.now().minusYears(1).withDayOfYear(startDate.lengthOfYear());

        Set<Long> resourceIds = tenantResources.stream().map(Resource::getId).collect(Collectors.toSet());

        List<WorkingHours> workingHoursForResources = workingHoursRepository.findAllByResource_IdIn(resourceIds);
        List<ScheduleOverride> overrides = scheduleOverrideRepository.findAllByResource_IdIn(resourceIds);

        Map<Long, List<WorkingHours>> workingHoursByResource = workingHoursForResources.stream()
                .collect(Collectors.groupingBy(wh -> wh.getResource().getId()));

        Map<Long, Map<LocalDate, ScheduleOverride>> overridesByResource = overrides.stream()
                .collect(Collectors.groupingBy(
                        o -> o.getResource().getId(),
                        Collectors.toMap(ScheduleOverride::getDate, o -> o)
                ));

        Map<Long, List<Booking>> bookingsByResource = previousYearBookings.stream()
                .collect(Collectors.groupingBy(b -> b.getResource().getId()));

        List<DistributionItemDto> result = new ArrayList<>();

        for (Venue venue : tenantVenues) {
            List<Resource> venueResources = tenantResources.stream()
                    .filter(r -> r.getVenue().getId().equals(venue.getId()))
                    .toList();

            BigDecimal totalWorkingHours = BigDecimal.ZERO;
            BigDecimal totalBookedHours = BigDecimal.ZERO;

            for (Resource resource : venueResources) {
                Long resourceId = resource.getId();
                List<WorkingHours> whList = workingHoursByResource.getOrDefault(resourceId, List.of());
                Map<LocalDate, ScheduleOverride> overrideMap = overridesByResource.getOrDefault(resourceId, Map.of());
                List<Booking> resourceBookings = bookingsByResource.getOrDefault(resourceId, List.of());

                LocalDate date = startDate;
                while (!date.isAfter(endDate)) {
                    ScheduleOverride override = overrideMap.get(date);

                    LocalTime openTime = null;
                    LocalTime closeTime = null;
                    boolean closed = false;

                    if (override != null) {
                        closed = Boolean.TRUE.equals(override.getClosed());
                        if (!closed) {
                            openTime = override.getOpenTime();
                            closeTime = override.getCloseTime();
                        }
                    } else {
                        LocalDate currentDate = date;
                        DayOfWeek dow = date.getDayOfWeek();
                        WorkingHours wh = whList.stream()
                                .filter(w -> w.getDayOfWeek() == dow)
                                .filter(w -> !currentDate.isBefore(w.getValidFrom()))
                                .filter(w -> w.getValidTo() == null || !currentDate.isAfter(w.getValidTo()))
                                .findFirst()
                                .orElse(null);

                        if (wh != null && !Boolean.TRUE.equals(wh.getClosed())) {
                            openTime = wh.getOpenTime();
                            closeTime = wh.getCloseTime();
                        } else {
                            closed = true;
                        }
                    }

                    if (!closed && openTime != null && closeTime != null && closeTime.isAfter(openTime)) {
                        Duration duration = Duration.between(openTime, closeTime);
                        BigDecimal hours = BigDecimal.valueOf(duration.toMinutes())
                                .divide(BigDecimal.valueOf(60), 4, RoundingMode.HALF_UP);
                        totalWorkingHours = totalWorkingHours.add(hours);
                    }

                    date = date.plusDays(1);
                }

                for (Booking booking : resourceBookings) {
                    Duration duration = Duration.between(booking.getStartTime(), booking.getEndTime());
                    BigDecimal hours = BigDecimal.valueOf(duration.toMinutes())
                            .divide(BigDecimal.valueOf(60), 4, RoundingMode.HALF_UP);
                    totalBookedHours = totalBookedHours.add(hours);
                }
            }

            BigDecimal percentage = BigDecimal.ZERO;
            if (totalWorkingHours.compareTo(BigDecimal.ZERO) > 0) {
                percentage = totalBookedHours
                        .divide(totalWorkingHours, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
            }

            DistributionItemDto dto = new DistributionItemDto();
            dto.setId(venue.getId());
            dto.setName(venue.getName());
            dto.setPercentage(percentage);
            result.add(dto);
        }

        return result;
    }

    private <T, K> Map<T, K> createTimeSeriesMap(TimeSeriesType type) {
        Map<T, K> result = new HashMap<>();
        LocalDate today = LocalDate.now();

        switch (type) {
            case DAILY_30_DAYS -> {
                for (int i = 0; i < 30; i++) {
                    LocalDate date = today.minusDays(i);
                    result.put((T) date, (K) new TimeSeriesPointDto(date, BigDecimal.ZERO));
                }
            }

            case WEEKLY_12_WEEKS -> {
                LocalDate startOfThisWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                for (int i = 0; i < 12; i++) {
                    LocalDate weekStart = startOfThisWeek.minusWeeks(i);
                    result.put((T) weekStart, (K) new TimeSeriesPointDto(weekStart, BigDecimal.ZERO));
                }
            }

            case MONTHLY_12_MONTHS -> {
                LocalDate startOfMonth = today.withDayOfMonth(1);
                for (int i = 0; i < 12; i++) {
                    LocalDate monthStart = startOfMonth.minusMonths(i);
                    result.put((T) monthStart, (K) new TimeSeriesPointDto(monthStart, BigDecimal.ZERO));
                }
            }

            case QUARTERLY -> {
                for (int i = 1; i <= 4; i++) {
                    result.put((T) Integer.valueOf(i), (K) BigDecimal.ZERO);
                }
            }
        }

        return result;
    }
}
