package co.unicauca.edu.autoevaluacioneswebapp.controllers;

import java.time.LocalDate;
import java.util.Set;

import co.unicauca.edu.autoevaluacioneswebapp.model.*;
import co.unicauca.edu.autoevaluacioneswebapp.services.ProfessorTypeService;
import co.unicauca.edu.autoevaluacioneswebapp.services.RoleService;
import co.unicauca.edu.autoevaluacioneswebapp.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import co.unicauca.edu.autoevaluacioneswebapp.services.UserService;

@Controller
public class UsersController {

    private UserService userService;
    private UserRoleService userRoleService;
    private RoleService roleService;

    private ProfessorTypeService professorTypeService;

    @Autowired
    public UsersController(UserService userService, UserRoleService userRoleService, RoleService roleService, ProfessorTypeService professorTypeService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.professorTypeService = professorTypeService;
    }

    @GetMapping("/proffesor-management")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String ListProffesors(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            model.addAttribute("proffesors", userService.findAllByRole("ROLE_DOCENTE"));
            return "proffessor-management";
        } else {
            return "access-denied";
        }
    }


    @GetMapping("/create-proffesor")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String createProffesorForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserEntity user = new UserEntity();
            model.addAttribute("user", user);
            return "create-proffessor";
        } else {
            return "access-denied";
        }

    }

    @PostMapping("/create-proffesor")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String saveProffesor(@ModelAttribute("user") UserEntity user) {
        /*
         * Añadir Validacion de usuario y redirrecion a la pagina
         */
        ProfessorType professorType = professorTypeService.findByName(String.valueOf(user.getProfessorType().getName()));
        user.setProfessorType(professorType);
        Role role = roleService.findByName("ROLE_DOCENTE");
        user.setPassword(user.getPersonalId().toString());
        user.setUserRoles(
                Set.of(UserRole.builder()
                        .role(role)
                        .initDate(LocalDate.now())
                        .finishDate(LocalDate.now().plusYears(1))
                        .user(user)
                        .build())
        );

        userService.save(user);
        return "redirect:/create-proffesor";
    }


}
