package co.unicauca.edu.autoevaluacioneswebapp.repositories;

import co.unicauca.edu.autoevaluacioneswebapp.model.UserEntity;
import co.unicauca.edu.autoevaluacioneswebapp.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    public UserRole findByUser(UserEntity userEntity);
}
    