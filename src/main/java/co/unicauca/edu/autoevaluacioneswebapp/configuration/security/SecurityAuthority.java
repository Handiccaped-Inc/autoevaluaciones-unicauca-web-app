package co.unicauca.edu.autoevaluacioneswebapp.configuration.security;

import co.unicauca.edu.autoevaluacioneswebapp.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class SecurityAuthority implements GrantedAuthority{

    private final Role rol;
    @Override
    public String getAuthority() {
        return rol.getName().toString();
    }
}
