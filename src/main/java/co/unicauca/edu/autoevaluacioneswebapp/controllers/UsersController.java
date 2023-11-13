package co.unicauca.edu.autoevaluacioneswebapp.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import co.unicauca.edu.autoevaluacioneswebapp.model.ERole;
import co.unicauca.edu.autoevaluacioneswebapp.model.Role;
import co.unicauca.edu.autoevaluacioneswebapp.model.UserEntity;
import co.unicauca.edu.autoevaluacioneswebapp.services.UserService;

@Controller
public class UsersController {

    private UserService userService;

    @Autowired
    public UsersController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/create-proffesor")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String createProffesorForm(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.getPrincipal() instanceof UserDetails) {
        UserEntity user  = new UserEntity();
        model.addAttribute("user", user);
        return "create-professor";
    }else{
        return "Error-Auth";
    }

    }
    
    @PostMapping("/create-proffesor")
    public String saveProffesor(@ModelAttribute("user") UserEntity user){
        user.setPassword(user.getPersonalId().toString());
        user.setRole(Role.builder()
                                .name(ERole.ROLE_DOCENTE)
                                .initDate(LocalDate.now())
                                .finishDate(LocalDate.now().plusYears(1))
                                .build());
        userService.save(user);
        return "redirect:/create-proffesor";
    }


}
