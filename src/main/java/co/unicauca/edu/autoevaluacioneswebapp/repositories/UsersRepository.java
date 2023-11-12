package co.unicauca.edu.autoevaluacioneswebapp.repositories;

import co.unicauca.edu.autoevaluacioneswebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String Email);
}
