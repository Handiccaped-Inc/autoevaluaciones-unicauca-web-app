package co.unicauca.edu.autoevaluacioneswebapp.controllers;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import co.unicauca.edu.autoevaluacioneswebapp.model.Labour;
import co.unicauca.edu.autoevaluacioneswebapp.services.ILabourService;

@Controller
@RequestMapping("/labours")
public class LaboursController {
    ILabourService labourService;

    @Autowired
    public LaboursController(ILabourService labourService) {
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
    public String createLabour(@ModelAttribute("labour") Labour labour, Model model) {
        labourService.save(labour);
        return "redirect:/labours/labour-management";
    }

    @GetMapping("/edit-labour/{name}")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String editLabourForm(@PathVariable String name, Model model) {
        Labour labour = labourService.findByLabourName(name)
                .orElseThrow(() -> new NoSuchElementException("Labor no encontrado con el nombre: " + name));
        model.addAttribute("labour", labour);
        return "edit-labour";
    }

    @PostMapping("/edit-labour/{name}")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String editLabour(@PathVariable String name, @ModelAttribute("labour") Labour updatedLabour) {
        Labour labour = labourService.findByLabourName(name)
                .orElseThrow(() -> new NoSuchElementException("Labor no encontrada con el nombre: " + name));

        labour.setLabourName(updatedLabour.getLabourName());
        labour.setType(updatedLabour.getType());
        labour.setActive(updatedLabour.isActive());
        labour.setAssignedHours(updatedLabour.getAssignedHours());

        labourService.save(labour);

        return "redirect:/labours/labour-management";
    }

}
