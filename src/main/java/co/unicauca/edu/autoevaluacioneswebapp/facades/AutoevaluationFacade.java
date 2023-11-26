package co.unicauca.edu.autoevaluacioneswebapp.facades;


import co.unicauca.edu.autoevaluacioneswebapp.model.Autoevaluation;
import co.unicauca.edu.autoevaluacioneswebapp.model.EAutoevaluationState;
import co.unicauca.edu.autoevaluacioneswebapp.model.EProfessorType;
import co.unicauca.edu.autoevaluacioneswebapp.model.Labour;
import co.unicauca.edu.autoevaluacioneswebapp.model.ProfessorType;
import co.unicauca.edu.autoevaluacioneswebapp.model.UserEntity;
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
        EProfessorType type = autoevaluation.getUserRole().getUser().getProfessorType().getName();
        int hours = autoevaluation.getLabour().getAssignedHours();
        String labourcode = autoevaluation.getLabour().getType().getCode();
        System.out.println(type+" "+hours+" "+labourcode+"VEEEEER");
        if(autoevaluationvalidatedhours(type, hours,labourcode)){
             autoevaluationService.save(autoevaluation);
        }
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

    public boolean autoevaluationvalidatedhours(EProfessorType type, int hours, String labourcode)
    {
     if(labourcode == "D"){
        if(type == EProfessorType.valueOf("PLANTA_TIEMPO_COMPLETO") && (((hours/16) <= 16 && (hours/16) >= 6))){
            return true;
        }else if(type == EProfessorType.valueOf("PLANTA_MEDIO_TIEMPO") && (((hours/16) <= 8 && (hours/16) >= 4))){
            return true;
        }else if(type == EProfessorType.valueOf("CATEDRA_TIEMPO_COMPLETO") && (((hours/16) <= 18 && (hours/16) >= 16))){
            return true;
        }else if(type == EProfessorType.valueOf("CATEDRA_MEDIO_TIEMPO") && (((hours/16) <= 14 && (hours/16) >= 12))){
            return true;
        }else{
            return false;
        }
    }else if(labourcode == "PI" || labourcode == "TI" )
    {
        if(hours <= 20 && hours >= 1){
            return true;
        }
     }else if(labourcode == "TD" || labourcode == "AS" || labourcode == "AS" || labourcode == "S" ){
        if(type == EProfessorType.valueOf("PLANTA_TIEMPO_COMPLETO") && (((hours/16) <= 16 && (hours/16) >= 6))){
            return true;
        }else if(type == EProfessorType.valueOf("PLANTA_MEDIO_TIEMPO") && (((hours/16) <= 8 && (hours/16) >= 4))){
            return true;
        }else if(type == EProfessorType.valueOf("CATEDRA_TIEMPO_COMPLETO") && (((hours/16) <= 18 && (hours/16) >= 16))){
            return true;
        }else if(type == EProfessorType.valueOf("CATEDRA_MEDIO_TIEMPO") && (((hours/16) <= 14 && (hours/16) >= 12))){
            return true;
        }else{
            return false;
        }
        }else if(labourcode == "AD"){

            if(hours <= 20 && hours >= 2){
                return true;
            }
        }
    return false;

    }
}
