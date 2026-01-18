package atlassports.model;

import lombok.Data;

import jakarta.persistence.*;
@Entity
@Data
public class SportType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
