package co.unicauca.edu.autoevaluacioneswebapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Cascade(CascadeType.PERSIST)
    private UserEntity user;


    @ManyToOne
    @JoinColumn(name = "role_id")
    @Cascade(CascadeType.PERSIST)
    private Role role;

    private LocalDate initDate;
    private LocalDate finishDate;

    @OneToMany(mappedBy = "userRole")
    private List<Autoevaluation> autoevaluations;

}
