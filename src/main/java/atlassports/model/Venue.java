package atlassports.model;

import atlassports.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private Boolean deleted;
}
