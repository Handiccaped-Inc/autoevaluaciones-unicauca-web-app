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
                                                .code("D")
                                                .description("Docencia")
                                                .build())
                                .assignedHours(100)
                                .active(true)
                                .build();
                Labour labor2 = Labour.builder()
                                .labourName("Ingeneria De Requisitos")
                                .type(LabourType.builder()
                                                .code("TD")
                                                .description("Trabajos Docencia")
                                                .build())
                                .assignedHours(100)
                                .active(true)
                                .build();

                Labour labor3 = Labour.builder()
                                .labourName("Testing de Software")
                                .type(LabourType.builder()
                                                .code("PI")
                                                .description("Proyectos Investigación")
                                                .build())
                                .assignedHours(40)
                                .active(true)
                                .build();

                Labour labor4 = Labour.builder()
                                .labourName("Gestión de Proyectos")
                                .type(LabourType.builder()
                                                .code("TI")
                                                .description("Trabajos Investigación")
                                                .build())
                                .assignedHours(35)
                                .active(true)
                                .build();

                Labour labor5 = Labour.builder()
                                .labourName("Desarrollo Frontend")
                                .type(LabourType.builder()
                                                .code("AD")
                                                .description("Administración")
                                                .build())
                                .assignedHours(45)
                                .active(true)
                                .build();

                Labour labor6 = Labour.builder()
                                .labourName("Desarrollo Backend")
                                .type(LabourType.builder()
                                                .code("AS")
                                                .description("Asesoría")
                                                .build())
                                .assignedHours(50)
                                .active(true)
                                .build();

                Labour labor7 = Labour.builder()
                                .labourName("Control de Calidad")
                                .type(LabourType.builder()
                                                .code("S")
                                                .description("Servicios")
                                                .build())
                                .assignedHours(35)
                                .active(true)
                                .build();

                Labour labor8 = Labour.builder()
                                .labourName("Análisis de Datos")
                                .type(LabourType.builder()
                                                .code("E")
                                                .description("Extensión")
                                                .build())
                                .assignedHours(40)
                                .active(true)
                                .build();

                Labour labor9 = Labour.builder()
                                .labourName("Seguridad Informática")
                                .type(LabourType.builder()
                                                .code("C")
                                                .description("Capacitación")
                                                .build())
                                .assignedHours(55)
                                .active(true)
                                .build();

                Labour labor10 = Labour.builder()
                                .labourName("Desarrollo Mobile")
                                .type(LabourType.builder()
                                                .code("OS")
                                                .description("Otros Servicios")
                                                .build())
                                .assignedHours(48)
                                .active(true)
                                .build();

                laboursRepository.saveAllAndFlush(List.of(
                                labor1, labor2, labor3, labor4, labor5,
                                labor6, labor7, labor8, labor9, labor10));
        }

}
