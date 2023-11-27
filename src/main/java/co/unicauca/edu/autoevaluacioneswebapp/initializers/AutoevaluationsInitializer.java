package co.unicauca.edu.autoevaluacioneswebapp.initializers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import co.unicauca.edu.autoevaluacioneswebapp.model.AcademicPeriod;
import co.unicauca.edu.autoevaluacioneswebapp.model.Autoevaluation;
import co.unicauca.edu.autoevaluacioneswebapp.model.EAutoevaluationState;
import co.unicauca.edu.autoevaluacioneswebapp.model.EProfessorType;
import co.unicauca.edu.autoevaluacioneswebapp.model.ERole;
import co.unicauca.edu.autoevaluacioneswebapp.model.Labour;
import co.unicauca.edu.autoevaluacioneswebapp.model.LabourType;
import co.unicauca.edu.autoevaluacioneswebapp.model.ProfessorType;
import co.unicauca.edu.autoevaluacioneswebapp.model.Role;
import co.unicauca.edu.autoevaluacioneswebapp.model.UserEntity;
import co.unicauca.edu.autoevaluacioneswebapp.model.UserRole;
import co.unicauca.edu.autoevaluacioneswebapp.model.Autoevaluation.AutoevaluationBuilder;
import co.unicauca.edu.autoevaluacioneswebapp.repositories.AutoevaluationRepository;
import co.unicauca.edu.autoevaluacioneswebapp.repositories.LaboursRepository;
import co.unicauca.edu.autoevaluacioneswebapp.repositories.UserRoleRepository;
import co.unicauca.edu.autoevaluacioneswebapp.services.LabourService;
import co.unicauca.edu.autoevaluacioneswebapp.services.RoleService;
import co.unicauca.edu.autoevaluacioneswebapp.services.UserRoleService;


public class AutoevaluationsInitializer implements ApplicationRunner {
    UserRoleRepository userRoleRepository;
    LabourService labourService;
    AutoevaluationRepository autoevaluationRepository;
      PasswordEncoder passwordEncoder;
      RoleService roleService;

    public AutoevaluationsInitializer( UserRoleRepository userRoleRepository,LabourService labourService ,AutoevaluationRepository autoevaluationRepository,  PasswordEncoder passwordEncoder, RoleService roleService){
        this.autoevaluationRepository = autoevaluationRepository;
        this.userRoleRepository = userRoleRepository;
        this.labourService = labourService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;

    }
    

       @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Labour> labour1 = labourService.findByLabourName("Ingeneria De software");
        Optional<UserRole> userR1 = userRoleRepository.findById(1L);
        Labour labor1;
        UserRole user1;

        if(labour1.isPresent()){
            labor1 = labour1.get();
        }else{
            labor1 = Labour.builder()
            .labourName("Ingeneria De software III")
            .type(LabourType.builder()
                    .code("CC")
                    .description("Labor de Jojan")
                    .build())
            .assignedHours(30)
            .active(true)
            .build();
        }

        if(userR1.isPresent()){
            user1 = userR1.get();
            
        }else{
            user1 = userRoleRepository.saveAndFlush(UserRole.builder()
                .user(UserEntity.builder()
                        .firstName("mario")
                        .lastName("perdomo")
                        .email("marioPerdomo@unicauca.edu.co")
                        .password(passwordEncoder.encode("123456"))
                        .personalId(123456789L)
                        .typePersonalId("CC")
                        .professorType(ProfessorType.builder()
                                .name(EProfessorType.CATEDRA_MEDIO_TIEMPO)
                                .build())
                        .build())
                .role(roleService.findByName("ROLE_DOCENTE"))
                .initDate(AcademicPeriod.getInitDate())
                .finishDate(AcademicPeriod.getEndDate())
                .build());
        }

        Autoevaluation autoevaluation1 = Autoevaluation.builder()
                .labour(labor1)
                .act(true)
                .initDate(AcademicPeriod.getInitDate())
                .finishDate(AcademicPeriod.getEndDate())
                .result("Este es un resultado")
                .evaluation(90)
                .userRole(user1)
                .state(EAutoevaluationState.EJECUCION)
                .build();
    

        /*Autoevaluation autoevaluation2 = Autoevaluation.builder()
                .labour(labour2.get())
                .act(true)
                .initDate(AcademicPeriod.getInitDate())
                .finishDate(AcademicPeriod.getEndDate())
                .result("Este es un resultado")
                .evaluation(200)
                .userRole(userRole2.get())
                .state(EAutoevaluationState.EJECUCION)
                .build();

                 autoevaluationRepository.saveAllAndFlush(List.of(autoevaluation2,autoevaluation1));*/
    autoevaluationRepository.saveAllAndFlush(List.of(autoevaluation1));

    }


    
}
