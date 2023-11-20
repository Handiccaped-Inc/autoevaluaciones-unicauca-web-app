package co.unicauca.edu.autoevaluacioneswebapp.controllers;

import co.unicauca.edu.autoevaluacioneswebapp.configuration.security.SecurityAuthority;
import co.unicauca.edu.autoevaluacioneswebapp.configuration.security.SecurityUser;
import co.unicauca.edu.autoevaluacioneswebapp.facades.AutoevaluationFacade;
import co.unicauca.edu.autoevaluacioneswebapp.model.Autoevaluation;
import co.unicauca.edu.autoevaluacioneswebapp.model.Labour;
import co.unicauca.edu.autoevaluacioneswebapp.model.UserRole;
import co.unicauca.edu.autoevaluacioneswebapp.services.ILabourService;
import co.unicauca.edu.autoevaluacioneswebapp.services.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    //TODO: Este metodo muestra autoevaluaciones, con las adiciones correctas se puede usar para redirigir a la vista de añadir
    // Una autoevaluacion para el usario con id userid, esto se puede ver segun yo de mejor forma desde UserRole, para no agregar una por una
    // sino con un checkbox
    @GetMapping("/user-autoevaluations/{userId}")
    @PreAuthorize("hasRole('ROLE_COORDINADOR') or hasRole('ROLE_DOCENTE')")
    public String getUserAutoevaluations(@AuthenticationPrincipal SecurityUser userDetails, @PathVariable Long userId, Model model) {
        UserRole userRole = userRoleService.findByUserId(userId);
        boolean isCoordinator = userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_COORDINADOR"));
        if (!isCoordinator && !userDetails.getUserEntity().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permitido ver esas autoevaluaciones");
        }
        List<Autoevaluation> autoevaluations = autoevaluationFacade.findAutoevaluationsByUserId(userId);
        model.addAttribute("autoevaluations", autoevaluations);
        model.addAttribute("userRole", userRole);
        model.addAttribute("userId", userId);
        return "user-autoevaluations";
    }
    //FIXME: es esto necesario?, podria simplemente crearlo en plural, seleccionar labores con checkbox, y crear
    //un objeto autoevaluacion por cada una, luego simplemente asignarselas a userrole y de esa forma no tendria que agregar una por una
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
