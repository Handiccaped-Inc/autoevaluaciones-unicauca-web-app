package co.unicauca.edu.autoevaluacioneswebapp.services;

import co.unicauca.edu.autoevaluacioneswebapp.model.ERole;
import co.unicauca.edu.autoevaluacioneswebapp.model.Role;
import co.unicauca.edu.autoevaluacioneswebapp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService{
    RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        ERole role = ERole.valueOf(name);
        return roleRepository.findByName(role).orElseThrow();
    }
}
