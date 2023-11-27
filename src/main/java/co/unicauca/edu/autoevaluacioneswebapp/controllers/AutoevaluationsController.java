package co.unicauca.edu.autoevaluacioneswebapp.controllers;

import co.unicauca.edu.autoevaluacioneswebapp.configuration.security.SecurityAuthority;
import co.unicauca.edu.autoevaluacioneswebapp.configuration.security.SecurityUser;
import co.unicauca.edu.autoevaluacioneswebapp.facades.AutoevaluationFacade;
import co.unicauca.edu.autoevaluacioneswebapp.model.*;
import co.unicauca.edu.autoevaluacioneswebapp.services.ILabourService;
import co.unicauca.edu.autoevaluacioneswebapp.services.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
    @PreAuthorize("hasRole('ROLE_COORDINADOR')  or hasRole('ROLE_DECANO')")
    public String getAutoevaluations(@AuthenticationPrincipal SecurityUser userDetails, Model model) {
        boolean isDecano = userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_DECANO"));
        List<Autoevaluation> autoevaluations;
        autoevaluations = autoevaluationFacade.findAll();
        if (isDecano) {
            List<Autoevaluation> autoevaluationsToSend = new ArrayList<>();
            for (Autoevaluation autoevaluation : autoevaluations) {
                if (autoevaluation.getUserRole().getRole().getName().equals(ERole.valueOf("ROLE_COORDINADOR"))) {
                    autoevaluationsToSend.add(autoevaluation);
                }
            }
            model.addAttribute("autoevaluations", autoevaluationsToSend);
            model.addAttribute("userId", userDetails.getId());
            return "autoevaluation-management";
        }
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

        if (isCoordinator) {
            return "user-autoevaluations-coordinator";
        }

        return "user-autoevaluations-professor";
    }

    @GetMapping("/add-autoevaluation/{userId}")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String addAutoevaluationForm(@PathVariable Long userId, Model model) {
        List<Labour> labours = labourService.findAll();
        Labour selectedLabour = new Labour();

        Autoevaluation autoevaluation = new Autoevaluation();

        model.addAttribute("autoevaluation", autoevaluation);
        model.addAttribute("labours", labours);
        model.addAttribute("selectedLabour", selectedLabour);
        return "add-autoevaluation";
    }

    @PostMapping("/add-autoevaluation")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String addAutoevaluation(@ModelAttribute("autoevaluation") Autoevaluation autoevaluation,
            @ModelAttribute("selectedLabour") Labour selectedLabour, @RequestParam("userIdP") String userIdP,
            Model model) {
        UserRole user = userRoleService.findByUserId(Long.parseLong(userIdP));
        selectedLabour = labourService.findById(selectedLabour.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la labor"));
        Autoevaluation autoevaluationsave = new Autoevaluation();
        autoevaluationsave.setUserRole(user);
        autoevaluationsave.setLabour(selectedLabour);
        autoevaluationsave.setState(autoevaluation.getState());
        autoevaluationsave.setInitDate(AcademicPeriod.getInitDate());
        autoevaluationsave.setFinishDate(AcademicPeriod.getEndDate());
        autoevaluationsave.setAct(autoevaluation.isAct());
        autoevaluationFacade.save(autoevaluationsave);
        return "redirect:/autoevaluations/autoevaluation-management";
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

    @GetMapping("ShowProffesor-autoevaluation")
    @PreAuthorize("hasRole('ROLE_DOCENTE')")
    public String ShowProffesorAutoevaluation(@AuthenticationPrincipal SecurityUser userDetails, Model model) {
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

    @GetMapping("perform-autoevaluation/{autoevaluationId}")
    @PreAuthorize("hasRole('ROLE_DOCENTE')")
    public String perfomAutoevaluation(@AuthenticationPrincipal SecurityUser userDetails,
            @PathVariable Long autoevaluationId, Model model) {
        UserRole userRole = userRoleService.findByUserId(userDetails.getUserEntity().getId());
        Autoevaluation autoevaluation = autoevaluationFacade.findAutoevaluationbyId(autoevaluationId)
                .orElseThrow(() -> new NoSuchElementException("Autoevaluacion no Encontrada "));
        model.addAttribute("autoevaluation", autoevaluation);
        model.addAttribute("userRole", userRole);
        return "realize-autoevaluation";
    }

    @PostMapping("perform-autoevaluation/{autoevaluationId}")
    @PreAuthorize("hasRole('ROLE_DOCENTE')")
    public String performAutoevaluation(@AuthenticationPrincipal SecurityUser userDetails,
            @ModelAttribute("autoevaluation") Autoevaluation updatedAutoevaluation, @PathVariable Long autoevaluationId,
            Model model) {
        Autoevaluation autoevaluation = autoevaluationFacade.findAutoevaluationbyId(autoevaluationId)
                .orElseThrow(() -> new NoSuchElementException("Autoevaluacion no Encontrada "));
        autoevaluation.setEvaluation(updatedAutoevaluation.getEvaluation());
        autoevaluation.setResult(updatedAutoevaluation.getResult());
        autoevaluation.setState(EAutoevaluationState.TERMINADO);
        autoevaluationFacade.save(autoevaluation);
        return "redirect:/autoevaluations/ShowProffesor-autoevaluation";
    }

}
