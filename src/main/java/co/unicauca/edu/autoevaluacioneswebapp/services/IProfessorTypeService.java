package co.unicauca.edu.autoevaluacioneswebapp.services;

import co.unicauca.edu.autoevaluacioneswebapp.model.ProfessorType;

public interface IProfessorTypeService {
    public ProfessorType findByName(String name);
}
