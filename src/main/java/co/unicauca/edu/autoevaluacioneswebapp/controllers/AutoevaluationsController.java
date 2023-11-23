package co.unicauca.edu.autoevaluacioneswebapp.controllers;

import co.unicauca.edu.autoevaluacioneswebapp.configuration.security.SecurityAuthority;
import co.unicauca.edu.autoevaluacioneswebapp.configuration.security.SecurityUser;
import co.unicauca.edu.autoevaluacioneswebapp.facades.AutoevaluationFacade;
import co.unicauca.edu.autoevaluacioneswebapp.model.*;
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
    public AutoevaluationsController(AutoevaluationFacade autoevaluationFacade, IUserRoleService userRoleService,
            ILabourService labourService) {
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
        model.addAttribute("userId", userDetails.getId());
        return "autoevaluation-management";
    }

    @GetMapping("/user-autoevaluations/{userId}")
    @PreAuthorize("hasRole('ROLE_COORDINADOR') or hasRole('ROLE_DOCENTE')")
    public String getUserAutoevaluations(@AuthenticationPrincipal SecurityUser userDetails, @PathVariable Long userId,
            Model model) {
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

    @GetMapping("/add-autoevaluation/{userId}")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String addAutoevaluationForm(@PathVariable Long userId, Model model) {
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
        return "add-autoevaluation";
    }

    @PostMapping("/add-autoevaluation")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String addAutoevaluation(@ModelAttribute("autoevaluation") Autoevaluation autoevaluation,
            @ModelAttribute("selectedLabour") Labour selectedLabour, Model model) {
        selectedLabour = labourService.findById(selectedLabour.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la labor"));
        autoevaluation.setLabour(selectedLabour);
        autoevaluation.setState(EAutoevaluationState.EJECUCION);
        autoevaluation.setInitDate(AcademicPeriod.getInitDate());
        autoevaluation.setFinishDate(AcademicPeriod.getEndDate());
        // TODO: Añadir los otros campos que no estan en el formulario
        autoevaluationFacade.save(autoevaluation);
        Long userId = autoevaluation.getUserRole().getUser().getId();
        return "redirect:/autoevaluations/user-autoevaluations/" + userId;
    }

    @GetMapping("autoevaluations-report")
    @PreAuthorize("hasAnyAuthority('ROLE_COORDINADOR', 'ROLE_DECANO')")
    public String getReport(@AuthenticationPrincipal SecurityUser userDetails, Model model) {
        int totalAutoevaluations = autoevaluationFacade.countAll();
        int done = autoevaluationFacade.countByState(EAutoevaluationState.TERMINADO);
        model.addAttribute("total", totalAutoevaluations);
        model.addAttribute("done", done);
        model.addAttribute("userId", userDetails.getId());
        return "autoevaluations-report";
    }

    @GetMapping("perform-autoevaluation")
    @PreAuthorize("hasRole('ROLE_DOCENTE')")
    public String performAutoevaluation(@AuthenticationPrincipal SecurityUser userDetails, Model model) {
        UserRole userRole = userRoleService.findByUserId(userDetails.getUserEntity().getId());
        List<Autoevaluation> autoevaluations = userRole.getAutoevaluations();
        List<Autoevaluation> autoevaluationsToPerform = new ArrayList<>();
        for (Autoevaluation autoevaluation : autoevaluations) {
            if (autoevaluation.getState().equals(EAutoevaluationState.EJECUCION)) {
                autoevaluationsToPerform.add(autoevaluation);
            }
        }
        model.addAttribute("autoevaluations", autoevaluationsToPerform);
        model.addAttribute("userRole", userRole);
        model.addAttribute("userId", userDetails.getId());
        return "perform-autoevaluation";
    }

    @PostMapping("perform-autoevaluation")
    @PreAuthorize("hasRole('ROLE_DOCENTE')")
    public String performAutoevaluation(@ModelAttribute("autoevaluations") List<Autoevaluation> autoevaluations,
            Model model) {
        for (Autoevaluation autoevaluation : autoevaluations) {
            autoevaluation.setState(EAutoevaluationState.TERMINADO);
            autoevaluationFacade.save(autoevaluation);
        }
        return "redirect:/autoevaluations/perform-autoevaluation";
    }
}
