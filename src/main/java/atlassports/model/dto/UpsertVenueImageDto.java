package atlassports.model.dto;

import lombok.Data;

@Data
public class UpsertVenueImageDto {
    private Long venueId;
    private String imageUrl;
    private Boolean deleted;
}
