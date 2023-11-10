package co.unicauca.edu.autoevaluacioneswebapp.services;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private final Environment environment;

    @Autowired
    public JwtService(Environment env) {
        this.environment = env;
    }

    public boolean isTokenExpired(String token) {
        try {
            return this.getClaims(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException ex) {
            return false;
        }

    }

    private Claims getClaims(String token) {
        String secretKey = this.environment.getProperty("jwt.secret.key");
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
        return claimsJws.getBody();
    }

    public UsernamePasswordAuthenticationToken getAuthFromToken(String token) {
        return this.getAuthToken(this.getClaims(token));
    }

    private UsernamePasswordAuthenticationToken getAuthToken(Claims claims) {
        @SuppressWarnings("unchecked") List<String> roles = (List<String>) claims.get("roles");
        Collection<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new UsernamePasswordAuthenticationToken(
                claims.get("sub"), claims.get("user_id"), authorities
        );
    }
}

