package com.toyproject.notTodoList.core.configuration.security;

import com.toyproject.notTodoList.domain.auth.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@RequiredArgsConstructor
@Configuration
public class AuthenticationManagerConfig {
    private final ObjectPostProcessor<Object> objectPostProcessor;
    @Bean
    public AuthenticationManager authenticationManager(
            JwtAuthenticationProvider jwtAuthenticationProvider) throws Exception {
        return new AuthenticationManagerBuilder(objectPostProcessor)
                .authenticationProvider(jwtAuthenticationProvider).build();
    }
}
