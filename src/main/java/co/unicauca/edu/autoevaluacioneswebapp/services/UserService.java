package co.unicauca.edu.autoevaluacioneswebapp.services;

import co.unicauca.edu.autoevaluacioneswebapp.model.ERole;
import co.unicauca.edu.autoevaluacioneswebapp.model.UserEntity;
import co.unicauca.edu.autoevaluacioneswebapp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    UsersRepository usersRepository;

    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return this.usersRepository.findByEmail(email);
    }

    @Override
    public UserEntity save(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.usersRepository.save(user);
    }

    @Override
    public UserEntity update(UserEntity user) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(Long id) {
        usersRepository.deleteById(id);
    }

    @Override
    public List<UserEntity> findAllByRole(String roleName) {
        ERole role = ERole.valueOf(roleName);
        LocalDate now = LocalDate.now();
        return usersRepository.findByUserRoles_Role_NameAndUserRoles_InitDateLessThanEqualAndUserRoles_FinishDateGreaterThanEqual(role, now,now);
    }

    @Override
    public List<UserEntity> search(String search) {
        long id;
        if(search.chars().allMatch( Character::isDigit)){
            id = Long.parseLong(search);}else{ id = 0L;}
       return usersRepository.findByPersonalIdOrTypePersonalIdOrFirstNameOrLastNameOrProfessorTypeOrActive(id, search, search, search, null, Boolean.parseBoolean(search) );
    }
}
   
