package co.unicauca.edu.autoevaluacioneswebapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    public SecurityFilterChain filterChain(HttpSecurity httpsecurity) throws Exception {
        return httpsecurity
                .authorizeHttpRequests((reqs) -> reqs
                        .requestMatchers("auth")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                        )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .sessionManagement((ssmg)->ssmg.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();

    }
}
