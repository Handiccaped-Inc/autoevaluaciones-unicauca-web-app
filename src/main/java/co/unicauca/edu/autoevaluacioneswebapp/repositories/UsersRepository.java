package co.unicauca.edu.autoevaluacioneswebapp.repositories;

import co.unicauca.edu.autoevaluacioneswebapp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UsersRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String Email);
}
