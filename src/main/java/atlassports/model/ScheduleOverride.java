package atlassports.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
public class ScheduleOverride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Resource resource;
    private LocalDate date;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Boolean closed;
    private String reason;
}
