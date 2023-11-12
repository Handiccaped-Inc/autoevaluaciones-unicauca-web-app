/**
 * Represents a work entity in the system.
 * <p>
 * Each work entity has a unique identifier, name, type identifier, and assigned hours.
 *
 * @author Santiago Agredo Vallejo
 * @version 1.0
 * @since 2023-11-11
 */
package co.unicauca.edu.autoevaluacioneswebapp.model;

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
public class Labour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private String labourName;

    private long labourType;

    private int assignedHours;

}
