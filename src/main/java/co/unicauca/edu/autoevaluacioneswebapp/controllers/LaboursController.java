package co.unicauca.edu.autoevaluacioneswebapp.controllers;

import co.unicauca.edu.autoevaluacioneswebapp.model.Labour;
import co.unicauca.edu.autoevaluacioneswebapp.model.UserEntity;
import co.unicauca.edu.autoevaluacioneswebapp.services.LabourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/labours")
public class LaboursController {
    LabourService labourService;


    @Autowired
    public LaboursController(LabourService labourService){
        this.labourService = labourService;
    }


    @GetMapping("/labour-management")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String listLabours(Model model) {
        model.addAttribute("labours", labourService.findAll());
        return "labour-management";
    }


    @GetMapping("/create-labour")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String createLabourForm(Model model) {

        Labour labour = new Labour();
        model.addAttribute("labour", labour);
        return "create-labour";

    }


    @PostMapping("/create-labour")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String createLabour(@ModelAttribute("labour") Labour labour, Model model){
        labourService.save(labour);
        return "redirect:/labours/labour-management";
    }

}
