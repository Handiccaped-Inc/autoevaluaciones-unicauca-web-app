package co.unicauca.edu.autoevaluacioneswebapp.configuration.security;

import co.unicauca.edu.autoevaluacioneswebapp.model.UserEntity;
import co.unicauca.edu.autoevaluacioneswebapp.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class SecurityUser implements UserDetails {
    private final UserEntity userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<UserRole> userRoles = userEntity.getUserRoles();
        LocalDate now = LocalDate.now();
        // Mapear los roles que esten activos en la fecha actual
        return userRoles.stream()
                .filter(userRole -> !userRole.getInitDate().isAfter(now) && !userRole.getFinishDate().isBefore(now))
                .map(userRole -> new SecurityAuthority(userRole.getRole()))
                .toList();
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getEmail();
    }

    public Long getId() {
        return userEntity.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
        // return userEntity.isActive();
    }
}
