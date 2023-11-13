package co.unicauca.edu.autoevaluacioneswebapp.initializers;

import co.unicauca.edu.autoevaluacioneswebapp.model.ERole;
import co.unicauca.edu.autoevaluacioneswebapp.model.ProfessorType;
import co.unicauca.edu.autoevaluacioneswebapp.model.Role;
import co.unicauca.edu.autoevaluacioneswebapp.model.UserEntity;
import co.unicauca.edu.autoevaluacioneswebapp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
public class UserInitializer implements ApplicationRunner {
    UsersRepository usersRepository;

    @Autowired
    public UserInitializer(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        usersRepository.saveAllAndFlush(List.of(
                UserEntity.builder()
                        .firstName("Pablo")
                        .lastName("Ruiz")
                        .email("prestrepo@unicauca.edu.co")
                        .password("123456")
                        .role(Role.builder()
                                .name(ERole.ROLE_DECANO)
                                .initDate(LocalDate.now())
                                .finishDate(LocalDate.now().plusYears(1))
                                .build())
                        .personalId(123456789L)
                        .typePersonalId("CC")
                        .professorTypes(Set.of(ProfessorType.TIEMPO_COMPLETO))
                        .build(),
                UserEntity.builder()
                        .firstName("Jojan")
                        .lastName("Serna")
                        .email("jeserna@unicauca.edu.co")
                        .password("123456")
                        .role(Role.builder()
                                .name(ERole.ROLE_COORDINADOR)
                                .initDate(LocalDate.now())
                                .finishDate(LocalDate.now().plusYears(1))
                                .build())
                        .personalId(123456789L)
                        .typePersonalId("CC")
                        .professorTypes(Set.of(ProfessorType.CATEDRA))
                        .build(),
                UserEntity.builder()
                        .firstName("Santiago")
                        .lastName("Agredo")
                        .email("sagredov@unicauca.edu.co")
                        .password("123456")
                        .role(Role.builder()
                                .name(ERole.ROLE_DECANO)
                                .initDate(LocalDate.now())
                                .finishDate(LocalDate.now().plusYears(1))
                                .build())
                        .personalId(123456789L)
                        .typePersonalId("CC")
                        .professorTypes(Set.of(ProfessorType.PLANTA))
                        .build()
        ));
    }
}
