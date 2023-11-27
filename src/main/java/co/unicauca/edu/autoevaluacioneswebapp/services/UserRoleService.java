package co.unicauca.edu.autoevaluacioneswebapp.services;

import co.unicauca.edu.autoevaluacioneswebapp.model.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.unicauca.edu.autoevaluacioneswebapp.model.UserRole;
import co.unicauca.edu.autoevaluacioneswebapp.repositories.UserRoleRepository;

import java.time.LocalDate;
import java.util.List;

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

    @Override
    public List<UserRole> findAllByRole(ERole role) {
         LocalDate now = LocalDate.now();
        return userRoleRepository.findByRole_NameAndInitDateLessThanEqualAndFinishDateGreaterThanEqual(role, now, now);
    }

    @Override
    public UserRole findByUserId(Long userId) {
        LocalDate now = LocalDate.now();
        return userRoleRepository.findByUserIdAndInitDateLessThanEqualAndFinishDateGreaterThanEqual(userId, now, now);
    }


}
