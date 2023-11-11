/**
 * Represents a user in the system.
 * <p>
 * Each user has a unique identifier, personal identifier, type identifier, first name, last name, email, and password.
 * The user can also have one or more roles and one or more professor types.
 *
 * @author Jojan Esteban Serna Serna - Santiago Agredo Vallejo
 * @version 1.0
 * @since 2023-11-11
 */
package co.unicauca.edu.autoevaluacioneswebapp.entities;

import co.unicauca.edu.autoevaluacioneswebapp.model.Role;
import co.unicauca.edu.autoevaluacioneswebapp.model.professorType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    /**
     * The unique identifier of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
      /**
     * The personal identifier of the user.
     */
    private Long personalId;
    /**
     * The type identifier of the user.
     */
    private String typeId;
     /**
     * The first name of the user.
     */
    private String firstName;
    /**
     * The last name of the user.
     */
    private String lastName;
     /**
     * The email of the user.
     */
    private String email;
    
    /**
     * The password of the user.
     */
    private String password;


    /**
     * The set of roles assigned to the user.
     */
    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    /**
     * The set of professor types assigned to the user.
     */
    @ElementCollection(targetClass = professorType.class)
    @Enumerated(EnumType.STRING)
    private Set<professorType> professorTypes;


}
