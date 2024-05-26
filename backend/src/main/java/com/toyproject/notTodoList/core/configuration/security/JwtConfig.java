package com.toyproject.notTodoList.core.configuration.security;


import com.toyproject.notTodoList.core.properties.JwtProperties;
import com.toyproject.notTodoList.domain.auth.jwt.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@RequiredArgsConstructor
@Configuration
public class JwtConfig {
    private final JwtProperties jwtProperties;
    @Bean
    protected JwtHelper jwt() {
        return new JwtHelper(
                jwtProperties.getIssuer(),
                jwtProperties.getClientSecret(),
                jwtProperties.getAccessTokenExpiryHour(),
                jwtProperties.getRefreshTokenExpiryHour(),
                Date::new
        );
    }
}
