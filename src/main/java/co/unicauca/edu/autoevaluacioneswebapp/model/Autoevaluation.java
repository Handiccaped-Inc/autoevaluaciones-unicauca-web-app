/**
 * Represents an autoevaluation entity in the system.
 * <p>
 * Each autoevaluation has a unique identifier, work identifier, activity status,
 * start date, finish date, estate, result, and evaluation type.
 * <p>
 *
 * @author Santiago Agredo
 * @version 1.0
 * @since 2023-11-11
 */
package co.unicauca.edu.autoevaluacioneswebapp.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Autoevaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL) //Cambidado para las prubeas Cambiar EN version final
    private Labour labour;

    private boolean act;

    private LocalDate initDate;

    private LocalDate finishDate;

    @Enumerated(EnumType.STRING)
    private EAutoevaluationState state;

    private String result;

    @Max(value = 100)
    private long evaluation;

    @ManyToOne(cascade = CascadeType.MERGE) //Cambiado Para El Inicializar Version final colocoar Persite si no genera error
    private UserRole userRole;
}
