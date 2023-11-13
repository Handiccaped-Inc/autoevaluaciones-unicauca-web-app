package co.unicauca.edu.autoevaluacioneswebapp.services;

import co.unicauca.edu.autoevaluacioneswebapp.model.User;
import co.unicauca.edu.autoevaluacioneswebapp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService {
    UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }
    public Optional<User> find(String email){
        return  this.usersRepository.findByEmail(email);
    }
}
