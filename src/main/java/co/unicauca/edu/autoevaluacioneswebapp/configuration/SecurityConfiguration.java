package co.unicauca.edu.autoevaluacioneswebapp.configuration;


import co.unicauca.edu.autoevaluacioneswebapp.filters.UserAuthenticationFilter;
import co.unicauca.edu.autoevaluacioneswebapp.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final JwtService jwtService;

    @Autowired
    public SecurityConfiguration(JwtService jwtService){
        this.jwtService = jwtService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests((authz) -> authz.anyRequest().authenticated())
                .addFilterBefore(new UserAuthenticationFilter(jwtService),UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((sess)-> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        http.headers((hds)-> hds.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();
    }

}

