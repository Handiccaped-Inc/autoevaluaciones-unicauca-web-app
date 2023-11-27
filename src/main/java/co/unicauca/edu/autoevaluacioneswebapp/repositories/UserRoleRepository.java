package co.unicauca.edu.autoevaluacioneswebapp.repositories;

import co.unicauca.edu.autoevaluacioneswebapp.model.ERole;
import co.unicauca.edu.autoevaluacioneswebapp.model.UserEntity;
import co.unicauca.edu.autoevaluacioneswebapp.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    public Optional<UserRole>findByUser(UserEntity userEntity);
    List<UserRole> findByRole_NameAndInitDateLessThanEqualAndFinishDateGreaterThanEqual(ERole name, LocalDate now, LocalDate now1);

    public Optional<UserRole> findByUserEmail(String email);

}
