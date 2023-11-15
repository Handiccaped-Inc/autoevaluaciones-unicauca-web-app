package co.unicauca.edu.autoevaluacioneswebapp.services;

import co.unicauca.edu.autoevaluacioneswebapp.model.Role;

public interface IRoleService {
    public Role findByName(String name);
}
