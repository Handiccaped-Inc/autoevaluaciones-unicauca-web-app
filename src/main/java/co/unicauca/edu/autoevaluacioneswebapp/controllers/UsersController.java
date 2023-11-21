package co.unicauca.edu.autoevaluacioneswebapp.controllers;

import java.time.LocalDate;
import java.util.Set;
import java.util.NoSuchElementException;

import co.unicauca.edu.autoevaluacioneswebapp.model.*;
import co.unicauca.edu.autoevaluacioneswebapp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UsersController {

    private IUserService userService;
    private IUserRoleService userRoleService;
    private IRoleService roleService;

    private IProfessorTypeService professorTypeService;

    @Autowired
    public UsersController(IUserService userService, IUserRoleService userRoleService, IRoleService roleService,
            IProfessorTypeService professorTypeService) {
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
        ProfessorType professorType = professorTypeService
                .findByName(String.valueOf(user.getProfessorType().getName()));
        user.setProfessorType(professorType);
        Role role = roleService.findByName("ROLE_DOCENTE");
        user.setPassword(user.getPersonalId().toString());
        user.setUserRoles(
                Set.of(UserRole.builder()
                        .role(role)
                        .initDate(LocalDate.now())
                        .finishDate(LocalDate.now().plusYears(1))
                        .user(user)
                        .build()));

        userService.save(user);
        return "redirect:/users/professor-management";

    }

    @GetMapping("/edit-professor/{email}")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String editProfessorForm(@PathVariable String email, Model model) {
        UserEntity user = userService.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con el correo: " + email));
        model.addAttribute("user", user);
        return "edit-professor";
    }

    @PostMapping("/edit-professor/{email}")
    @PreAuthorize("hasRole('ROLE_COORDINADOR')")
    public String editProfessor(@PathVariable String email, @ModelAttribute("professor") UserEntity updatedUser) {
        UserEntity user = userService.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con el correo: " + email));

        user.setPersonalId(updatedUser.getPersonalId());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setProfessorType(
                professorTypeService.findByName(String.valueOf(updatedUser.getProfessorType().getName())));
        user.setActive(updatedUser.isActive());
        user.setTypePersonalId(updatedUser.getTypePersonalId());
        user.setEmail(updatedUser.getEmail());

        userService.save(user);

        return "redirect:/users/professor-management";
    }

}