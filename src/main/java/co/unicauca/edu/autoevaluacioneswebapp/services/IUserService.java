package co.unicauca.edu.autoevaluacioneswebapp.services;

import co.unicauca.edu.autoevaluacioneswebapp.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    public Optional<UserEntity> findByEmail(String email);
    /*public List<UserEntity> findAllByRole(String role);*/
    public UserEntity save(UserEntity user);
    public UserEntity update(UserEntity user);
    public void deleteById(Long id);
}
