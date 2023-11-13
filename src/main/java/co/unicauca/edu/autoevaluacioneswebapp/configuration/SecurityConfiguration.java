package co.unicauca.edu.autoevaluacioneswebapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    public SecurityFilterChain filterChain(HttpSecurity httpsecurity) throws Exception {
        return httpsecurity
                .authorizeHttpRequests((reqs) -> reqs
                        .requestMatchers("/public/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin((flg) -> {
                            flg.permitAll();
                            flg.successHandler(successHandler());
                        }
                )
                .sessionManagement((ssmg) -> {
                            ssmg.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                                    .invalidSessionUrl("/login")
                                    .maximumSessions(1)
                                    .expiredUrl("/login");
                            ssmg.sessionFixation().migrateSession();
                        }
                )
                .build();

    }

    public AuthenticationSuccessHandler successHandler() {
        return ((request, response, authentication) -> response.sendRedirect("/index"));
    }
}
