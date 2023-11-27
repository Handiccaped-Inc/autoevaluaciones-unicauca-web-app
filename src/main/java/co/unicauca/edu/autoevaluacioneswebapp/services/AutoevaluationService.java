package co.unicauca.edu.autoevaluacioneswebapp.services;


import co.unicauca.edu.autoevaluacioneswebapp.facades.AutoevaluationFacade;
import co.unicauca.edu.autoevaluacioneswebapp.model.Autoevaluation;
import co.unicauca.edu.autoevaluacioneswebapp.model.EAutoevaluationState;

import co.unicauca.edu.autoevaluacioneswebapp.repositories.AutoevaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AutoevaluationService implements IAutoevaluationService {
    AutoevaluationRepository autoevaluationRepository;

    @Autowired
    public AutoevaluationService(AutoevaluationRepository autoevaluationRepository) {
        this.autoevaluationRepository = autoevaluationRepository;
    }

    @Override
    public void save(Autoevaluation autoevaluation) {
        autoevaluationRepository.save(autoevaluation);
    }

    @Override
    public List<Autoevaluation> findByUserId(Long userId) {
        LocalDate now = LocalDate.now();
        return autoevaluationRepository.findByUserRole_User_IdAndInitDateLessThanEqualAndFinishDateGreaterThanEqual(userId, now, now);
    }

    @Override
    public List<Autoevaluation> findAll() {
        LocalDate now = LocalDate.now();
        return  autoevaluationRepository.findByInitDateLessThanAndFinishDateGreaterThan(now, now);
    }

    @Override
    public int countAll() {
        LocalDate now = LocalDate.now();
        return autoevaluationRepository.countByInitDateLessThanAndFinishDateGreaterThan(now, now);
    }

    @Override
    public int countByState(EAutoevaluationState state) {
        LocalDate now = LocalDate.now();
        return autoevaluationRepository.countByInitDateLessThanAndFinishDateGreaterThanAndState(now,now, state);
    }

    @Override
    public Optional<Autoevaluation> findById(Long autoEvaluationID) {
        return autoevaluationRepository.findById(autoEvaluationID);
    }
}
