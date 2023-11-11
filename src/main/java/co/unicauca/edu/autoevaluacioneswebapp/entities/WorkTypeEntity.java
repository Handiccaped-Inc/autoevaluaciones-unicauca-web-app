/**
 * Represents a type of work in the system.
 * <p>
 * Each work type has a unique identifier, code, and description.
 * <p>
 *
 * @author Santiago Agredo Vallejo
 * @version 1.0
 * @since 2023-11-11
 */
package co.unicauca.edu.autoevaluacioneswebapp.entities;

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
public class WorkType {
     /**
     * Unique identifier for the work type.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
      /**
     * Code associated with the work type.
     */
    private String code;
     /**
     * Description of the work type.
     */
    private String description;
}
