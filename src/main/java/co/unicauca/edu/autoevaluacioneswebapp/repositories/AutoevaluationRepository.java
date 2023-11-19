package co.unicauca.edu.autoevaluacioneswebapp.repositories;

import co.unicauca.edu.autoevaluacioneswebapp.model.Autoevaluation;
import co.unicauca.edu.autoevaluacioneswebapp.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AutoevaluationRepository extends JpaRepository<Autoevaluation, Long> {
    public List<Autoevaluation> findByUserRole(UserRole userRole);
    public List<Autoevaluation> findByUserRole_User_IdAndInitDateLessThanEqualAndFinishDateGreaterThanEqual(Long userId, LocalDate now, LocalDate now1);
}
