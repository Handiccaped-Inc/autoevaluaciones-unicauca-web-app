package co.unicauca.edu.autoevaluacioneswebapp.services;

import co.unicauca.edu.autoevaluacioneswebapp.model.ERole;
import co.unicauca.edu.autoevaluacioneswebapp.model.UserRole;

import java.util.List;

public interface IUserRoleService {
    public UserRole save(UserRole userRole);

    public List<UserRole> findAllByRole(ERole role);


    UserRole findByUserId(Long userId);
}
