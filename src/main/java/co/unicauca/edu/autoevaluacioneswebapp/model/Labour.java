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

import org.hibernate.annotations.Cascade;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Labour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String labourName;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "labour_type")
    private LabourType type;

    private int assignedHours;

    private boolean active = true;
}
