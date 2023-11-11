package co.unicauca.edu.autoevaluacioneswebapp.services;


import co.unicauca.edu.autoevaluacioneswebapp.entities.UserEntity;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.core.env.Environment;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import co.unicauca.edu.autoevaluacioneswebapp.model.Role;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final Environment environment;

    private final UserService userService;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder, Environment environment, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.environment = environment;
        this.userService = userService;
    }

    @Transactional
    public Optional<String> authUser(String email, String password) {
        // implementar get user by email
        Optional<UserEntity> user = this.userService.getUser(email);
        if(user.isEmpty() || !this.passwordEncoder.matches(password, user.get().getPassword())){
            return Optional.empty();
        }
        return Optional.of(this.buildJwtToken(user.get()));
    }

    private String buildJwtToken(UserEntity usr){
        Map<String, Object> claims = new HashMap<>();
        Collection<String> roles = usr.getRoles().stream().map(Role::toString).collect(Collectors.toList());
        claims.put("roles", roles);
        claims.put("user_id", usr.getId());
        String jwtKey = environment.getProperty("jwt.secret.key");
        return Jwts.builder()
                .setSubject(usr.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(SignatureAlgorithm.HS512, jwtKey)
                .addClaims(claims)
                .compact();
    }

}
