package com.project.electricitymanagement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the Electricity Management Application.
 *
 * @author Devesh.Chandra
 */
@OpenAPIDefinition(info = @Info(
        title = "Electricity Management Application",
        description = "SpringBoot Application for Electricity Management",
        version = "v1.0")
)
@SpringBootApplication
public class ElectricityManagementApplication {

    public static void main(final String[] args) {

        SpringApplication.run(ElectricityManagementApplication.class, args);
    }

}
