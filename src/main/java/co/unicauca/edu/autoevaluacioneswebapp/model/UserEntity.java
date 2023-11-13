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
package co.unicauca.edu.autoevaluacioneswebapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long personalId;

    private String typePersonalId;

     @NotBlank
     @Size(max = 30)
    private String firstName;

    @NotBlank
    @Size(max = 30)
    private String lastName;

     @Email
     @NotBlank
     @Size(max = 80)
    private String email;

    @NotBlank
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


    @ElementCollection(targetClass = ProfessorType.class)
    @Enumerated(EnumType.STRING)
    private Set<ProfessorType> professorTypes;



}
