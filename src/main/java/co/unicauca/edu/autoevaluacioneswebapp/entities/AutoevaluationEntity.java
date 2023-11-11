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
package co.unicauca.edu.autoevaluacioneswebapp.entities;

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

public class AutoevaluationEntity {
     /**
     * Unique identifier for the autoevaluation entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     /**
     * Identifier of the associated work for the autoevaluation.
     */
    private Long workId;
      /**
     * Activity status indicating whether the autoevaluation is active or not.
     */
    private boolean Act;
      /**
     * Start date of the autoevaluation.
     */
    private Date intiDate;
    /**
     * Finish date of the autoevaluation.
     */
    private Date finishData;
     /**
     * Current estate of the autoevaluation.
     */
    private String Estate;
     /**
     * Result of the autoevaluation.
     */
    private String result;
    /**
     * Identifier for the type of evaluation.
     */
    private long evaluation;

}
