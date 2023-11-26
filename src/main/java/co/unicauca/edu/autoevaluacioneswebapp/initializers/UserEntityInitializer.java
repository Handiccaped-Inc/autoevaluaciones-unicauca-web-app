package co.unicauca.edu.autoevaluacioneswebapp.initializers;

import co.unicauca.edu.autoevaluacioneswebapp.model.*;
import co.unicauca.edu.autoevaluacioneswebapp.repositories.ProfessorTypeRepository;
import co.unicauca.edu.autoevaluacioneswebapp.repositories.UsersRepository;
import co.unicauca.edu.autoevaluacioneswebapp.services.ProfessorTypeService;
import co.unicauca.edu.autoevaluacioneswebapp.services.RoleService;
import co.unicauca.edu.autoevaluacioneswebapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Set;


@Order(3)
public class UserEntityInitializer implements CommandLineRunner {
    
    UsersRepository usersRepository;
    ProfessorTypeRepository professorTypeRepository;
    ProfessorTypeService professorTypeService;
    UserService userService;
    
    RoleService roleService;
    
    public UserEntityInitializer(UsersRepository usersRepository, ProfessorTypeRepository professorTypeRepository, UserService userService, ProfessorTypeService professorTypeService, RoleService roleService){
        this.usersRepository = usersRepository;
        this.professorTypeRepository = professorTypeRepository;
        this.userService = userService;
        this.professorTypeService = professorTypeService;
        this.roleService = roleService;
    }
    @Override
    public void run(String... args) throws Exception {
        UserEntity user = UserEntity.builder()
                //Añadir aqui los atributos
                .build();

        ProfessorType professorType = professorTypeService
                .findByName(String.valueOf(EProfessorType.CATEDRA_MEDIO_TIEMPO));

        user.setProfessorType(professorType);

        Role role = roleService.findByName("ROLE_DOCENTE");

        user.setUserRoles(
                Set.of(UserRole.builder()
                        .role(role)
                        .initDate(AcademicPeriod.getInitDate())
                        .finishDate(AcademicPeriod.getEndDate())
                        .user(user)
                        .build()));

        userService.save(user);
    }

}
