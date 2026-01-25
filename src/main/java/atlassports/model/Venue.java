package atlassports.model;

import atlassports.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Data
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Tenant tenant;
    private String name;
    private String address;
    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;
    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;
    private String country;
    private String city;
    private Boolean hasParking;
    private Boolean isIndoor;
    private String phoneNumber;
    private String email;
    private String description;
    @ManyToMany
    List<SportType> sportTypes;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;
    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime modifiedAt;
    @Enumerated(EnumType.STRING)
    private EntityStatus status;
    private Boolean deleted = false;
}
