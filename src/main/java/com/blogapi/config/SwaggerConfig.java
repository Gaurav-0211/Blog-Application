package com.blogapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Blog Application API")
                        .version("1.0")
                        .description("API documentation for Blog Application")
                        .contact(new Contact().email("gaurav@gmail.com").name("Gaurav").url("abc@gmail.com")));
    }

}
