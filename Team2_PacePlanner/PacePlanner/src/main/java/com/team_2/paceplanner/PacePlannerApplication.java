package com.team_2.paceplanner;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@OpenAPIDefinition(info = @Info(title = "Pace Planner API", version = "1.0", description = "Documentation of Pace Planner API"))
public class PacePlannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PacePlannerApplication.class, args);
    }

}
