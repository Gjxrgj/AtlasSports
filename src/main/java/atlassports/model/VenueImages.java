package atlassports.model;

import lombok.Data;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class VenueImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Venue venue;
    private String imageUrl;
    @CreationTimestamp
    private LocalDateTime uploadedAt;
    private Boolean deleted;
}
