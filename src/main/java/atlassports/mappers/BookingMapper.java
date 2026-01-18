package atlassports.mappers;

import atlassports.model.Booking;
import atlassports.model.dto.BookingDto;
import atlassports.model.dto.UpsertBookingDto;
import org.mapstruct.Mapper;

import java.util.List;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "resourceId", target = "resource.id")
    Booking toEntity(UpsertBookingDto dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "resource.id", target = "resourceId")
    BookingDto toDto(Booking booking);

    List<BookingDto> toDto(List<Booking> bookings);

    @Mapping(source = "resourceId", target = "resource.id")
    Booking toEntity(UpsertBookingDto dto, @MappingTarget Booking booking);

    List<BookingDto> toDtoList(List<Booking> all);
}
