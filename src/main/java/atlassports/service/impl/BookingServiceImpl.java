package atlassports.service.impl;

import atlassports.enums.BookingStatus;
import atlassports.mappers.BookingMapper;
import atlassports.model.Booking;
import atlassports.model.Resource;
import atlassports.model.User;
import atlassports.model.Venue;
import atlassports.model.dto.BookingDto;
import atlassports.model.dto.UpsertBookingDto;
import atlassports.repository.BookingRepository;
import atlassports.repository.ResourceRepository;
import atlassports.repository.VenueRepository;
import atlassports.service.BookingService;
import atlassports.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final BookingMapper bookingMapper;
    private final VenueRepository venueRepository;
    private final ResourceRepository resourceRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, UserService userService, BookingMapper bookingMapper, VenueRepository venueRepository, ResourceRepository resourceRepository) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.bookingMapper = bookingMapper;
        this.venueRepository = venueRepository;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public Page<BookingDto> getBookings(Pageable pageable) {
        return bookingRepository
                .findAll(pageable)
                .map(bookingMapper::toDto);
    }

    @Override
    public Page<BookingDto> getBookingHistoryForMe(Pageable page) {
        User loggedInUser = userService.getLoggedInUser();
        return bookingRepository
                .findAllByUser_IdAndStatus(page, loggedInUser.getId(), BookingStatus.COMPLETED)
                .map(bookingMapper::toDto);
    }

    @Override
    public Page<BookingDto> getUpcomingBookingsMe(Pageable page) {
        User loggedInUser = userService.getLoggedInUser();
        return bookingRepository
                .findAllByUser_IdAndStatus(page, loggedInUser.getId(), BookingStatus.CONFIRMED)
                .map(bookingMapper::toDto);
    }

    @Override
    public BookingDto createBooking(UpsertBookingDto upsertBookingDto) {
        Booking booking = bookingMapper.toEntity(upsertBookingDto);
        User loggedInUser = userService.getLoggedInUser();

        booking.setUser(loggedInUser);

        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    @Override
    public Page<BookingDto> getBookingsForTenant(Pageable page, Long tenantId) {
        Set<Long> venueIds = venueRepository
                .findAllByTenant_Id(tenantId)
                .stream()
                .map(Venue::getId)
                .collect(Collectors.toSet());

        Set<Long> resourceIds = resourceRepository
                .findAllByVenue_IdIn(venueIds)
                .stream()
                .map(Resource::getId)
                .collect(Collectors.toSet());

        return bookingRepository.findAlLByResource_IdIn(page, resourceIds).map(bookingMapper::toDto);
    }
}
