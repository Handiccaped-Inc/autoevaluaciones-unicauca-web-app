package co.unicauca.edu.autoevaluacioneswebapp.facades;


import co.unicauca.edu.autoevaluacioneswebapp.services.LabourService;
import co.unicauca.edu.autoevaluacioneswebapp.services.UserRoleService;
import co.unicauca.edu.autoevaluacioneswebapp.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class AutoevaluationFacade {
    private LabourService labourService;

    private UserRoleService userRoleService;

    private UserService userService;

    private AutoevaluationFacade(LabourService labourService, UserRoleService userRoleService, UserService userService){
        this.labourService = labourService;
        this.userRoleService = userRoleService;
        this.userService = userService;
    }
}
