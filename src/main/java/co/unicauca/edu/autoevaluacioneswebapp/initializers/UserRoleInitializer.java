package co.unicauca.edu.autoevaluacioneswebapp.initializers;

import co.unicauca.edu.autoevaluacioneswebapp.model.*;
import co.unicauca.edu.autoevaluacioneswebapp.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class UserRoleInitializer implements ApplicationRunner {
    UserRoleRepository userRoleRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserRoleInitializer(UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRoleRepository.saveAllAndFlush(List.of(
                UserRole.builder()
                        .user(UserEntity.builder()
                                .firstName("Pablo")
                                .lastName("Ruiz")
                                .email("prestrepo@unicauca.edu.co")
                                .password(passwordEncoder.encode("123456"))
                                .personalId(123456789L)
                                .typePersonalId("CC")
                                .professorType(ProfessorType.builder()
                                        .name(EProfessorType.TIEMPO_COMPLETO)
                                        .build())
                                .build())
                        .role(Role.builder()
                                .name(ERole.ROLE_DECANO)
                                .build())
                        .initDate(AcademicPeriod.getInitDate())
                        .finishDate(AcademicPeriod.getEndDate())
                        .build(),
                UserRole.builder()
                        .user(UserEntity.builder()
                                .firstName("Jojan")
                                .lastName("Serna")
                                .email("jeserna@unicauca.edu.co")
                                .password(passwordEncoder.encode("123456"))
                                .personalId(123456789L)
                                .typePersonalId("CC")
                                .professorType(ProfessorType.builder()
                                        .name(EProfessorType.CATEDRA)
                                        .build())
                                .build())
                        .role(Role.builder()
                                .name(ERole.ROLE_COORDINADOR)
                                .build())
                        .initDate(AcademicPeriod.getInitDate())
                        .finishDate(AcademicPeriod.getEndDate())
                        .build(),
                UserRole.builder()
                        .user(UserEntity.builder()
                                .firstName("Santiago")
                                .lastName("Agredo")
                                .email("sagredov@unicauca.edu.co")
                                .password(passwordEncoder.encode("123456"))
                                .personalId(123456789L)
                                .typePersonalId("CC")
                                .professorType(ProfessorType.builder()
                                        .name(EProfessorType.PLANTA)
                                        .build())
                                .build())
                        .role(Role.builder()
                                .name(ERole.ROLE_DOCENTE)
                                .build())
                        .initDate(AcademicPeriod.getInitDate())
                        .finishDate(AcademicPeriod.getEndDate())
                        .build()
        ));
    }
}
