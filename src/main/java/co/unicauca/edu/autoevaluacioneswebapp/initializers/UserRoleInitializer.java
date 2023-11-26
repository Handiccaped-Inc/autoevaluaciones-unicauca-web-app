package co.unicauca.edu.autoevaluacioneswebapp.initializers;

import co.unicauca.edu.autoevaluacioneswebapp.model.*;
import co.unicauca.edu.autoevaluacioneswebapp.repositories.ProfessorTypeRepository;
import co.unicauca.edu.autoevaluacioneswebapp.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Order(1)
public class UserRoleInitializer implements ApplicationRunner {
        UserRoleRepository userRoleRepository;
        ProfessorTypeRepository professorTypeRepository;
        PasswordEncoder passwordEncoder;

        @Autowired
        public UserRoleInitializer(UserRoleRepository userRoleRepository,
                        ProfessorTypeRepository professorTypeRepository, PasswordEncoder passwordEncoder) {
                this.userRoleRepository = userRoleRepository;
                this.passwordEncoder = passwordEncoder;
                this.professorTypeRepository = professorTypeRepository;
        }

        @Override
        public void run(ApplicationArguments args) throws Exception {
                UserRole user1 = UserRole.builder()
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
                                                .lastDegreeAchivement("Doctorado En Computación")
                                                .build())
                                .role(Role.builder()
                                                .name(ERole.ROLE_DECANO)
                                                .build())
                                .initDate(AcademicPeriod.getInitDate())
                                .finishDate(AcademicPeriod.getEndDate())
                                .build();

                UserRole user2 = UserRole.builder()
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
                                                .lastDegreeAchivement("Magister en Datos")
                                                .build())
                                .role(Role.builder()
                                                .name(ERole.ROLE_COORDINADOR)
                                                .build())
                                .initDate(AcademicPeriod.getInitDate())
                                .finishDate(AcademicPeriod.getEndDate())
                                .build();
                UserRole user3 = UserRole.builder()
                                .user(UserEntity.builder()
                                                .firstName("Santiago")
                                                .lastName("Agredo")
                                                .email("sagredov@unicauca.edu.co")
                                                .password(passwordEncoder.encode("123456"))
                                                .personalId(12345678911L)
                                                .typePersonalId("CC")
                                                .professorType(ProfessorType.builder()
                                                                .name(EProfessorType.PLANTA)
                                                                .build())
                                                .lastDegreeAchivement("Magister en seguridad")
                                                .build())
                                .role(Role.builder()
                                                .name(ERole.ROLE_DOCENTE)
                                                .build())
                                .initDate(AcademicPeriod.getInitDate())
                                .finishDate(AcademicPeriod.getEndDate())
                                .build();

                userRoleRepository.saveAllAndFlush(List.of(user1, user2, user3));
        }

}
