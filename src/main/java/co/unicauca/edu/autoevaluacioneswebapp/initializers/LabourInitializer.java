package co.unicauca.edu.autoevaluacioneswebapp.initializers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import co.unicauca.edu.autoevaluacioneswebapp.repositories.LaboursRepository;

public class LabourInitializer implements ApplicationRunner {
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
    
}
