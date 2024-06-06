package com.toyproject.notTodoList.core.configuration.swagger;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import java.util.List;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER,
        name = "refresh-token", description = "Refresh token received upon login"
)
@SecurityScheme(
        type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER,
        name = "Authorization", description = "Prefix Required! Add 'Bearer ' before token!"
)
public class SwaggerConfig{


    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("api")
                .addOpenApiCustomizer(customOpenApi())
                .pathsToMatch("/**")
                .build();
    }

    private OpenApiCustomizer customOpenApi (){
        return openApi -> openApi.info(
                new Info().title("Not-Todo-List API")
                        .description("Not-Todo-List API Documentation")
                        .version("alpha 1.0"))
                .addSecurityItem(new SecurityRequirement().addList("Authorization"))
                ;
    }

}