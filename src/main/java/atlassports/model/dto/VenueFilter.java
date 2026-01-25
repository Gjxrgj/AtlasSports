package atlassports.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VenueFilter {
    String city;
    String country;
    Boolean hasParking;
    Boolean isIndoor;

    BigDecimal userLatitude;
    BigDecimal userLongitude;
    BigDecimal radiusKm;

    String searchTerm;
}
