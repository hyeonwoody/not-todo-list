package com.toyproject.notTodoList.core.configuration.swagger;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import java.util.List;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER,
        name = "Authentication", description = "Prefix Required! Add 'Bearer ' before token!"
)
public class SwaggerConfig{

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("api")
                .addOpenApiCustomizer(customizer -> customizer.info(
                                new Info().title("Not-Todo-List API").description("Not-Todo-List API Documentation")
                                        .version("alpha 1.0"))
                        .security(List.of(new SecurityRequirement().addList("Authentication"))))
                .pathsToMatch("/**")
                .build();
    }

}