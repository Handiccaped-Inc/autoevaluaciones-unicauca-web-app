package co.unicauca.edu.autoevaluacioneswebapp.repositories;

import co.unicauca.edu.autoevaluacioneswebapp.model.ERole;
import co.unicauca.edu.autoevaluacioneswebapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface UsersRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String Email);

    List<UserEntity> findByUserRoles_Role_NameAndUserRoles_InitDateLessThanEqualAndUserRoles_FinishDateGreaterThanEqual(ERole name, LocalDate now, LocalDate now1);

    //@Query("SELECT u FROM UserEntity u JOIN u.role r WHERE r.name = ?1")
    //List<UserEntity> findAllByRole(ERole role);
}
