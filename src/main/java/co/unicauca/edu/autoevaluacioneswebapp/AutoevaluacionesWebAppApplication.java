package co.unicauca.edu.autoevaluacioneswebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AutoevaluacionesWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoevaluacionesWebAppApplication.class, args);
    }

}
