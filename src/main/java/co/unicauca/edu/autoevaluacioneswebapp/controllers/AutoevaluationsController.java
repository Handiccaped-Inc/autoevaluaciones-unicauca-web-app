package co.unicauca.edu.autoevaluacioneswebapp.controllers;

import co.unicauca.edu.autoevaluacioneswebapp.facades.AutoevaluationFacade;
import co.unicauca.edu.autoevaluacioneswebapp.model.Autoevaluation;
import co.unicauca.edu.autoevaluacioneswebapp.model.Labour;
import co.unicauca.edu.autoevaluacioneswebapp.model.UserRole;
import co.unicauca.edu.autoevaluacioneswebapp.services.ILabourService;
import co.unicauca.edu.autoevaluacioneswebapp.services.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/autoevaluations")
public class AutoevaluationsController {

    AutoevaluationFacade autoevaluationFacade;
    IUserRoleService userRoleService;
    ILabourService labourService;

    @Autowired
    public AutoevaluationsController(AutoevaluationFacade autoevaluationFacade, IUserRoleService userRoleService, ILabourService labourService) {
        this.autoevaluationFacade = autoevaluationFacade;
        this.userRoleService = userRoleService;
        this.labourService = labourService;
    }

    //TODO: Este metodo obtiene las autoevaluaciones de un usuario basado en el id dado, implementar
    // el metodo para mostrar las autoevaluaciones de los usuarios en una lista con su nota total
    // desde ahi al presionar ver detalles se puede ver las autoevaluaciones con este metodo
    @GetMapping("/user-autoevaluations/{userId}")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String getUserAutoevaluations(@PathVariable Long userId, Model model) {
        UserRole userRole = userRoleService.findByUserId(userId);
        List<Autoevaluation> autoevaluations = autoevaluationFacade.findAutoevaluationsByUserId(userId);
        model.addAttribute("autoevaluations", autoevaluations);
        model.addAttribute("userRole", userRole);
        model.addAttribute("userId", userId);
        // Añadir el boton de agregar las autoevaluaciones que redirija a la vista de crear autoevaluaciones
        return "user-autoevaluations";
    }

    //TODO: Este es el metodo que se llama cuando se presiona Agregar fila, aqui se permite agregar una autoevaluacion
    // a un usuario, se debe implementar la vista para agregar la autoevaluacion
    @GetMapping("/create-autoevaluation/{userId}")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String createAutoevaluationForm(@PathVariable Long userId, Model model) {
        UserRole user = userRoleService.findByUserId(userId);
        List<Labour> labours = labourService.findAll();
        Autoevaluation autoevaluation = Autoevaluation.builder()
                .userRole(user)
                .build();
        
        //TODO: Con th:field crear los dropdown y los campos mapeados a EAutoevaluationState para que sea posible guardar
        model.addAttribute("autoevaluation", autoevaluation);
        model.addAttribute("user", user);
        model.addAttribute("labours", labours);
        return "redirect:/autoevaluations/user-autoevaluations/" + userId;
    }

    //TODO: Mejorar la logica para agregar los campos de las autoevaluaciones
    @PostMapping("/create-autoevaluation")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String createAutoevaluation(@ModelAttribute("autoevaluation") Autoevaluation autoevaluation, Model model) {
        autoevaluationFacade.save(autoevaluation);
        Long userId = autoevaluation.getUserRole().getUser().getId();
        return "redirect:/autoevaluations/user-autoevaluations/" + userId;
    }
}
