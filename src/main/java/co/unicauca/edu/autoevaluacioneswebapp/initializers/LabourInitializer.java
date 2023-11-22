package co.unicauca.edu.autoevaluacioneswebapp.initializers;

import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import co.unicauca.edu.autoevaluacioneswebapp.repositories.LaboursRepository;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class LabourInitializer implements ApplicationRunner, Ordered {
    LaboursRepository laboursRepository;

    @Autowired
    public LabourInitializer(LaboursRepository laboursRepository) {
        this.laboursRepository = laboursRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
