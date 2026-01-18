package atlassports.mappers;

import atlassports.model.SportType;
import atlassports.model.dto.SportTypeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SportTypeMapper {
    SportTypeDto toDto(SportType sportType);
    List<SportTypeDto> toDto(List<SportType> sportType);
}
