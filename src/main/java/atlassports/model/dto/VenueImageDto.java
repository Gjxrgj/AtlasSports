package atlassports.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VenueImageDto {
    private Long id;
    private Long venueId;
    private String imageUrl;
    private LocalDateTime uploadedAt;
    private Boolean deleted;
}
