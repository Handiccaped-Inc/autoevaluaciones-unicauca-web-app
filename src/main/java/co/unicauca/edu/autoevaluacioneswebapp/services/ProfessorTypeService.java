package co.unicauca.edu.autoevaluacioneswebapp.services;

import co.unicauca.edu.autoevaluacioneswebapp.model.EProfessorType;
import co.unicauca.edu.autoevaluacioneswebapp.model.ProfessorType;
import co.unicauca.edu.autoevaluacioneswebapp.repositories.ProfessorTypeRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorTypeService implements IProfessorTypeService{

    ProfessorTypeRepository professorTypeRepository;

    @Autowired
    public ProfessorTypeService(ProfessorTypeRepository professorTypeRepository) {
        this.professorTypeRepository = professorTypeRepository;
    }
    @Override
    public ProfessorType findByName(String name) {
        return professorTypeRepository.findByName(EProfessorType.valueOf(name)).orElseThrow();
    }
}
