package co.unicauca.edu.autoevaluacioneswebapp.repositories;

import co.unicauca.edu.autoevaluacioneswebapp.model.Autoevaluation;
import co.unicauca.edu.autoevaluacioneswebapp.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutoevaluationRepository extends JpaRepository<Autoevaluation, Long> {
    public List<Autoevaluation> findByUserRole(UserRole userRole);
}
