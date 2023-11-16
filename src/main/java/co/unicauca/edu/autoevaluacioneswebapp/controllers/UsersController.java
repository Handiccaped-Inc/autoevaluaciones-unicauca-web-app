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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RoleService roleService;

    private final ProfessorTypeService professorTypeService;

    @Autowired
    public UsersController(UserService userService, UserRoleService userRoleService, RoleService roleService, ProfessorTypeService professorTypeService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.professorTypeService = professorTypeService;
    }

    @GetMapping("/professor-management")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String listProfessors(Model model) {
        model.addAttribute("professors", userService.findAllByRole("ROLE_DOCENTE"));
        return "professor-management";

    }


    @GetMapping("/create-professor")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String createProfessorForm(Model model) {

        UserEntity user = new UserEntity();
        model.addAttribute("user", user);
        return "create-professor";


    }

    @PostMapping("/create-professor")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String createProfessor(@ModelAttribute("user") UserEntity user) {
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
        return "redirect:/create-professor";
    }


}
