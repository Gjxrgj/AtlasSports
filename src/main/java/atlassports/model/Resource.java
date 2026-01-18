package atlassports.model;

import atlassports.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Venue venue;
    private String name;
    private BigDecimal pricePerHourInEuro;
    @Enumerated(EnumType.STRING)
    private EntityStatus status;
    private Integer capacity = 1;
    private Boolean defaultResource = false;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
}
