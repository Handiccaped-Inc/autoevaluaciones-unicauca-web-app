package co.unicauca.edu.autoevaluacioneswebapp.filters;


import co.unicauca.edu.autoevaluacioneswebapp.services.JwtService;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public UserAuthenticationFilter(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = this.getTokenFromRequest(request);
        try{
            if(token.isPresent() && !this.jwtService.isTokenExpired(token.get())){
                UsernamePasswordAuthenticationToken authToken = this.jwtService
                        .getAuthFromToken(token.get());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }catch(JwtException ex){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }finally{
            filterChain.doFilter(request, response);
        }
    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request){
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader == null)
            return Optional.empty();

        if(!authHeader.startsWith("Bearer "))
            return Optional.empty();

        return Optional.of(authHeader.substring("Bearer ".length()));
    }



}

