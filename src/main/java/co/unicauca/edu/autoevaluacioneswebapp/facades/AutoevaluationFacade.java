package co.unicauca.edu.autoevaluacioneswebapp.facades;


import co.unicauca.edu.autoevaluacioneswebapp.model.Autoevaluation;
import co.unicauca.edu.autoevaluacioneswebapp.model.EAutoevaluationState;
import co.unicauca.edu.autoevaluacioneswebapp.model.Labour;
import co.unicauca.edu.autoevaluacioneswebapp.model.UserRole;
import co.unicauca.edu.autoevaluacioneswebapp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoevaluationFacade {
    private LabourService labourService;

    private UserRoleService userRoleService;

    private UserService userService;

    private IAutoevaluationService autoevaluationService;

    @Autowired
    public AutoevaluationFacade(LabourService labourService, UserRoleService userRoleService, UserService userService, AutoevaluationService autoevaluationService){
        this.labourService = labourService;
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.autoevaluationService = autoevaluationService;
    }

    public void save(Autoevaluation autoevaluation){
        System.out.println("LA AUTOLVEAUCON USERR:"+autoevaluation.getUserRole().getUser().getPersonalId());
        autoevaluationService.save(autoevaluation);
    }

    public List<Autoevaluation> findAutoevaluationsByUserId(Long userId) {
        return autoevaluationService.findByUserId(userId);
    }


    public List<Autoevaluation> findAll() {
        return autoevaluationService.findAll();
    }

    public int countAll() {
        return autoevaluationService.countAll();
    }

    public int countByState(EAutoevaluationState state) {
        return autoevaluationService.countByState(state);
    }
}
