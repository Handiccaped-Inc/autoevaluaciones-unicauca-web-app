package co.unicauca.edu.autoevaluacioneswebapp.services;

import co.unicauca.edu.autoevaluacioneswebapp.model.Autoevaluation;

import java.util.List;

public interface IAutoevaluationService {
    public void save(Autoevaluation autoevaluation);

    List<Autoevaluation> findByUserId(Long userId);
}
