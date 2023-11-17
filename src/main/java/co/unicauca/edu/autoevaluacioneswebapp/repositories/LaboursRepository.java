package co.unicauca.edu.autoevaluacioneswebapp.repositories;

import co.unicauca.edu.autoevaluacioneswebapp.model.Labour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface LaboursRepository extends JpaRepository<Labour, Long> {
    List<Labour> findByType_Code(String code);
    Optional<Labour> findFirstByLabourName(String name);

    List<Labour> findByLabourNameContainingIgnoreCase(String content);
}
