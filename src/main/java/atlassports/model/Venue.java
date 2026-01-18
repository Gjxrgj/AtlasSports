package atlassports.model;

import atlassports.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    @Enumerated(EnumType.STRING)
    private EntityStatus status;
    private Boolean deleted;
}
