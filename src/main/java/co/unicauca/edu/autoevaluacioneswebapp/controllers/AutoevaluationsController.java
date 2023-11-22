package co.unicauca.edu.autoevaluacioneswebapp.controllers;

import co.unicauca.edu.autoevaluacioneswebapp.configuration.security.SecurityAuthority;
import co.unicauca.edu.autoevaluacioneswebapp.configuration.security.SecurityUser;
import co.unicauca.edu.autoevaluacioneswebapp.facades.AutoevaluationFacade;
import co.unicauca.edu.autoevaluacioneswebapp.model.Autoevaluation;
import co.unicauca.edu.autoevaluacioneswebapp.model.EAutoevaluationState;
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

import java.util.ArrayList;
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

    @GetMapping("/autoevaluation-management")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String getAutoevaluations(@AuthenticationPrincipal SecurityUser userDetails, Model model) {
        List<Autoevaluation> autoevaluations;
        autoevaluations = autoevaluationFacade.findAll();
        model.addAttribute("autoevaluations", autoevaluations);
        return "autoevaluation-management";
    }

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

    @GetMapping("/create-autoevaluation/{userId}")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String createAutoevaluationForm(@PathVariable Long userId, Model model) {
        UserRole user = userRoleService.findByUserId(userId);
        List<Labour> labours = labourService.findAll();
        Labour selectedLabour = new Labour();

        Autoevaluation autoevaluation = Autoevaluation.builder()
                .userRole(user)
                .build();

        model.addAttribute("autoevaluation", autoevaluation);
        model.addAttribute("user", user);
        model.addAttribute("labours", labours);
        model.addAttribute("selectedLabour", selectedLabour);
        return "create-autoevaluation";
    }

    @PostMapping("/create-autoevaluation")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String createAutoevaluation(@ModelAttribute("autoevaluation") Autoevaluation autoevaluation, @ModelAttribute("selectedLabour") Labour selectedLabour, Model model) {
        selectedLabour = labourService.findById(selectedLabour.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la labor"));
        autoevaluation.setLabour(selectedLabour);
        //TODO: Añadir los otros campos que no estan en el formulario
        autoevaluationFacade.save(autoevaluation);
        Long userId = autoevaluation.getUserRole().getUser().getId();
        return "redirect:/autoevaluations/user-autoevaluations/" + userId;
    }


    @GetMapping("autoevaluations-report")
    @PreAuthorize("hasAnyAuthority('ROLE_COORDINADOR', 'ROLE_DECANO')")
    public String report(Model model) {
        int totalAutoevaluations = autoevaluationFacade.countAll();
        int done = autoevaluationFacade.countByState(EAutoevaluationState.TERMINADO);
        model.addAttribute("total", totalAutoevaluations);
        model.addAttribute("done", done);
        return "autoevaluations-report";
    }
}
