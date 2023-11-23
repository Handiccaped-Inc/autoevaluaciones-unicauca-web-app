package co.unicauca.edu.autoevaluacioneswebapp.initializers;

import jakarta.persistence.criteria.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import co.unicauca.edu.autoevaluacioneswebapp.model.Labour;
import co.unicauca.edu.autoevaluacioneswebapp.model.LabourType;
import co.unicauca.edu.autoevaluacioneswebapp.repositories.LaboursRepository;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class LabourInitializer implements ApplicationRunner {
    LaboursRepository laboursRepository;

    @Autowired
    public LabourInitializer(LaboursRepository laboursRepository) {
        this.laboursRepository = laboursRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Labour labor1 = Labour.builder()
                .labourName("Ingeneria De software")
                .type(LabourType.builder()
                        .code("CC")
                        .description("Labor de pablo")
                        .build())
                .assignedHours(30)
                .active(true)
                .build();
        Labour labor2 = Labour.builder()
                .labourName("Ingeneria De Requisitos")
                .type(LabourType.builder()
                        .code("JJ")
                        .description("Labor de Santiago")
                        .build())
                .assignedHours(50)
                .active(true)
                .build();

        Labour labor3 = Labour.builder()
                .labourName("Testing de Software")
                .type(LabourType.builder()
                        .code("QA")
                        .description("Labor de Ana")
                        .build())
                .assignedHours(40)
                .active(true)
                .build();

        Labour labor4 = Labour.builder()
                .labourName("Gestión de Proyectos")
                .type(LabourType.builder()
                        .code("PM")
                        .description("Labor de María")
                        .build())
                .assignedHours(35)
                .active(true)
                .build();

        Labour labor5 = Labour.builder()
                .labourName("Desarrollo Frontend")
                .type(LabourType.builder()
                        .code("FE")
                        .description("Labor de Juan")
                        .build())
                .assignedHours(45)
                .active(true)
                .build();

        Labour labor6 = Labour.builder()
                .labourName("Desarrollo Backend")
                .type(LabourType.builder()
                        .code("BE")
                        .description("Labor de Carla")
                        .build())
                .assignedHours(50)
                .active(true)
                .build();

        Labour labor7 = Labour.builder()
                .labourName("Control de Calidad")
                .type(LabourType.builder()
                        .code("QC")
                        .description("Labor de Marta")
                        .build())
                .assignedHours(35)
                .active(true)
                .build();

        Labour labor8 = Labour.builder()
                .labourName("Análisis de Datos")
                .type(LabourType.builder()
                        .code("AD")
                        .description("Labor de Alejandro")
                        .build())
                .assignedHours(40)
                .active(true)
                .build();

        Labour labor9 = Labour.builder()
                .labourName("Seguridad Informática")
                .type(LabourType.builder()
                        .code("SI")
                        .description("Labor de Andrea")
                        .build())
                .assignedHours(55)
                .active(true)
                .build();

        Labour labor10 = Labour.builder()
                .labourName("Desarrollo Mobile")
                .type(LabourType.builder()
                        .code("DM")
                        .description("Labor de Diego")
                        .build())
                .assignedHours(48)
                .active(true)
                .build();

        laboursRepository.saveAllAndFlush(List.of(
                labor1, labor2, labor3, labor4, labor5,
                labor6, labor7, labor8, labor9, labor10));
    }

}
