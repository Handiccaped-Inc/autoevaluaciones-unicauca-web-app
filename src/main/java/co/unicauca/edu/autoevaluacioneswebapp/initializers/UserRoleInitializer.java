package co.unicauca.edu.autoevaluacioneswebapp.initializers;

import co.unicauca.edu.autoevaluacioneswebapp.model.*;
import co.unicauca.edu.autoevaluacioneswebapp.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
public class UserRoleInitializer implements ApplicationRunner {
    UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleInitializer(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRoleRepository.saveAllAndFlush(List.of(
                UserRole.builder()
                        .user(UserEntity.builder()
                                .firstName("Pablo")
                                .lastName("Ruiz")
                                .email("prestrepo@unicauca.edu.co")
                                .password(new BCryptPasswordEncoder().encode("123456"))
                                .personalId(123456789L)
                                .typePersonalId("CC")
                                .professorType(ProfessorType.builder()
                                        .name(EProfessorType.TIEMPO_COMPLETO)
                                        .build())
                                .build())
                        .role(Role.builder()
                                .name(ERole.ROLE_DECANO)
                                .build())
                        .initDate(LocalDate.now())
                        .finishDate(LocalDate.now().plusYears(1))
                        .build(),
                UserRole.builder()
                        .user(UserEntity.builder()
                                .firstName("Jojan")
                                .lastName("Serna")
                                .email("jeserna@unicauca.edu.co")
                                .password(new BCryptPasswordEncoder().encode("123456"))
                                .personalId(123456789L)
                                .typePersonalId("CC")
                                .professorType(ProfessorType.builder()
                                        .name(EProfessorType.CATEDRA)
                                        .build())
                                .build())
                        .role(Role.builder()
                                .name(ERole.ROLE_COORDINADOR)
                                .build())
                        .initDate(LocalDate.now())
                        .finishDate(LocalDate.now().plusYears(1))
                        .build(),
                UserRole.builder()
                        .user(UserEntity.builder()
                                .firstName("Santiago")
                                .lastName("Agredo")
                                .email("sagredov@unicauca.edu.co")
                                .password(new BCryptPasswordEncoder().encode("123456"))
                                .personalId(123456789L)
                                .typePersonalId("CC")
                                .professorType(ProfessorType.builder()
                                        .name(EProfessorType.PLANTA)
                                        .build())
                                .build())
                        .role(Role.builder()
                                .name(ERole.ROLE_DOCENTE)
                                .build())
                        .initDate(LocalDate.now())
                        .finishDate(LocalDate.now().plusYears(1))
                        .build()
        ));
    }
}
