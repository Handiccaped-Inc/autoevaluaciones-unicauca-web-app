package co.unicauca.edu.autoevaluacioneswebapp.repositories;

import co.unicauca.edu.autoevaluacioneswebapp.model.EProfessorType;
import co.unicauca.edu.autoevaluacioneswebapp.model.ProfessorType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorTypeRepository extends JpaRepository<ProfessorType, Long> {
    public Optional<ProfessorType> findByName(EProfessorType name);
}
