package atlassports.mappers;

import atlassports.model.Subscription;
import atlassports.model.dto.SubscriptionDto;
import atlassports.model.dto.UpsertSubscriptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    @Mapping(source = "tenant.id", target = "tenantId")
    SubscriptionDto toDto(Subscription subscription);

    List<SubscriptionDto> toDto(List<Subscription> subscription);

    @Mapping(source = "tenantId", target = "tenant.id")
    Subscription toEntity(UpsertSubscriptionDto dto);

    @Mapping(source = "tenantId", target = "tenant.id")
    Subscription toEntity(UpsertSubscriptionDto dto, @MappingTarget Subscription subscription);

}
