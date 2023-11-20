package co.unicauca.edu.autoevaluacioneswebapp.services;

import co.unicauca.edu.autoevaluacioneswebapp.model.Labour;

import java.util.List;
import java.util.Optional;

public interface ILabourService {
    public List<Labour> findByTypeCode(String code);

    public void save(Labour labour);

    public Optional<Labour> findByLabourName(String name);

    public List<Labour> findByLabourNameContainingIgnoreCase(String content);


    public List<Labour> findAll();
}
