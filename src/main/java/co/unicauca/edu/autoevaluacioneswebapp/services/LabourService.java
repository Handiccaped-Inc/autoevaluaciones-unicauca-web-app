package co.unicauca.edu.autoevaluacioneswebapp.services;

import co.unicauca.edu.autoevaluacioneswebapp.model.Labour;
import co.unicauca.edu.autoevaluacioneswebapp.repositories.LaboursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabourService {
    LaboursRepository laboursRepository;

    @Autowired
    public LabourService(LaboursRepository laboursRepository){
        this.laboursRepository = laboursRepository;
    }

    public List<Labour> findByTypeCode(String code){
        return laboursRepository.findByType_Code(code);
    }

    public void save(Labour labour){
        laboursRepository.save(labour);
    }

    public Optional<Labour> findByLabourName(String name){
        return laboursRepository.findFirstByLabourName(name);
    }

    public List<Labour> findByLabourNameContainingIgnoreCase(String content){
        return laboursRepository.findByLabourNameContainingIgnoreCase(content);
    }


    public List<Labour> findAll(){
        return laboursRepository.findAll();
    }
}
