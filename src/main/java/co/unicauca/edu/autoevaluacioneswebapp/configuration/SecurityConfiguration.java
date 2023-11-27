package co.unicauca.edu.autoevaluacioneswebapp.configuration;

import co.unicauca.edu.autoevaluacioneswebapp.services.security.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
        private SecurityUserDetailsService userDetailsService;

        @Autowired
        public SecurityConfiguration(SecurityUserDetailsService userDetailsService) {
                this.userDetailsService = userDetailsService;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity httpsecurity) throws Exception {
                return httpsecurity

                                .authorizeHttpRequests((reqs) -> reqs
                                                .requestMatchers("/welcome", "/", "/resources/**", "/static/**",
                                                                "/css/**", "/img/**")
                                                .permitAll()
                                                .anyRequest()
                                                .authenticated())
                                .formLogin((flg) -> {
                                        flg.loginPage("/login")
                                                        .permitAll()
                                                        .successHandler(successHandler());

                                })
                                .logout((lgo) -> lgo
                                                .logoutSuccessUrl("/welcome")
                                                .permitAll())
                                .exceptionHandling((exh) -> exh
                                                .accessDeniedPage("/error/access-denied"))
                                .sessionManagement((ssmg) -> {
                                        ssmg.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                                                        .invalidSessionUrl("/login")
                                                        .maximumSessions(1)
                                                        .expiredUrl("/login");
                                        ssmg.sessionFixation().migrateSession();
                                })
                                .userDetailsService(userDetailsService)
                                .build();
        }

        public AuthenticationSuccessHandler successHandler() {
                return ((request, response, authentication) -> response.sendRedirect("/users/professor-management"));
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }

}
