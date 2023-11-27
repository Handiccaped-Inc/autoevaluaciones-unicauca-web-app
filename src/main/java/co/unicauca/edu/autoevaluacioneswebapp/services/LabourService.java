package co.unicauca.edu.autoevaluacioneswebapp.services;

import co.unicauca.edu.autoevaluacioneswebapp.model.Labour;
import co.unicauca.edu.autoevaluacioneswebapp.repositories.LaboursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabourService implements ILabourService{
    LaboursRepository laboursRepository;

    @Autowired
    public LabourService(LaboursRepository laboursRepository){
        this.laboursRepository = laboursRepository;
    }
    @Override
    public List<Labour> findByTypeCode(String code){
        return laboursRepository.findByType_Code(code);
    }
    @Override
    public void save(Labour labour){
        //TODO; Añadir la logica de negocio, validaciones de horas de acuerdo al tipo de labor
        laboursRepository.save(labour);
    }
    @Override
    public Optional<Labour> findByLabourName(String name){
        return laboursRepository.findFirstByLabourName(name);
    }
    @Override
    public List<Labour> findByLabourNameContainingIgnoreCase(String content){
        return laboursRepository.findByLabourNameContainingIgnoreCase(content);
    }

    @Override
    public List<Labour> findAll(){
        return laboursRepository.findAll();
    }
    @Override
    public Optional<Labour> findById(Long id) {
        return laboursRepository.findById(id);
    }
}
