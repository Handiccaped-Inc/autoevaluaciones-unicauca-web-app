/**
 * Represents a work entity in the system.
 * <p>
 * Each work entity has a unique identifier, name, type identifier, and assigned hours.
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

import java.util.Set;

import co.unicauca.edu.autoevaluacioneswebapp.model.Role;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LabourEntity {
    /**
     * Unique identifier for the work entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Name of the work entity.
     */
    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    private String labourName;
      /**
     * Identifier for the type of work.
     */
    private long labourType;
     /**
     * Number of assigned hours for the work entity.
     */
    private int assignedHours;

}
