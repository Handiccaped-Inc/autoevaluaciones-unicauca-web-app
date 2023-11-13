package co.unicauca.edu.autoevaluacioneswebapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole name;

    private LocalDate initDate;
    private LocalDate finishDate;
    
}
