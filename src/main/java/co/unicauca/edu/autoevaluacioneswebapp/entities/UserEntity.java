package co.unicauca.edu.autoevaluacioneswebapp.entities;

import co.unicauca.edu.autoevaluacioneswebapp.model.Role;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long personalId;
    private String typeId;
    private String firstName;

    private String lastName;

    private String email;
    private String password;


    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

}
