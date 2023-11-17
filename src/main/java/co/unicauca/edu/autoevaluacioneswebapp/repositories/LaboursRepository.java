package co.unicauca.edu.autoevaluacioneswebapp.repositories;

import co.unicauca.edu.autoevaluacioneswebapp.model.Labour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
public interface LaboursRepository extends JpaRepository<Labour, Long> {
    public List<Labour> findByType_Code(String code);
    public Optional<Labour> findFirstByLabourName(String name);
    
    public List<Labour> findByLabourNameContainingIgnoreCase(String content);
}
