package co.unicauca.edu.autoevaluacioneswebapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.unicauca.edu.autoevaluacioneswebapp.model.UserRole;
import co.unicauca.edu.autoevaluacioneswebapp.repositories.UserRoleRepository;

@Service
public class UserRoleService implements IUserRoleService{

     UserRoleRepository userRoleRepository;
     
     @Autowired
     public UserRoleService(UserRoleRepository userRoleRepository){
        this.userRoleRepository = userRoleRepository;
     }

    @Override
    public UserRole save(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }


}
