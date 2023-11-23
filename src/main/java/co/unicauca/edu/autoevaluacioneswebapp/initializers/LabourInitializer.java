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
        
            laboursRepository.saveAllAndFlush(List.of(labor1,labor2));
    }

}
