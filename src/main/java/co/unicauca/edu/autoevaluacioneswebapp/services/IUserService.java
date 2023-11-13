package co.unicauca.edu.autoevaluacioneswebapp.services;

import co.unicauca.edu.autoevaluacioneswebapp.model.UserEntity;

import java.util.Optional;

public interface IUserService {
    public Optional<UserEntity> findByEmail(String email);
}
