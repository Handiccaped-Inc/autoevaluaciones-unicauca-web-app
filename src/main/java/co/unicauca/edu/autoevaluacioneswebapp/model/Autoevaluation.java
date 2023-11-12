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

import java.sql.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Autoevaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long work;

    private boolean Act;

    private Date intiDate;

    private Date finishData;

    private String Estate;

    private String result;

    private long evaluation;

}
