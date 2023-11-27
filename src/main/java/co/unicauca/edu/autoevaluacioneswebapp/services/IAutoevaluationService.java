package co.unicauca.edu.autoevaluacioneswebapp.services;

import co.unicauca.edu.autoevaluacioneswebapp.model.Autoevaluation;
import co.unicauca.edu.autoevaluacioneswebapp.model.EAutoevaluationState;

import java.util.List;
import java.util.Optional;

public interface IAutoevaluationService {
    public void save(Autoevaluation autoevaluation);

    List<Autoevaluation> findByUserId(Long userId);

    List<Autoevaluation> findAll();

    Optional<Autoevaluation> findById(Long autoevaluationId);

    int countAll();

    int countByState(EAutoevaluationState state);
}
