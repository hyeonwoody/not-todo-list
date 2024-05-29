package com.toyproject.notTodoList.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyproject.notTodoList.domain.auth.oauth2.OAuth2AuthenticationProvider;

import com.toyproject.notTodoList.domain.auth.jwt.JwtToken;
import com.toyproject.notTodoList.domain.auth.presentation.dto.res.DetailLoginAPIResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;


@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {


    private final OAuth2AuthenticationProvider oAuth2AuthenticationProvider;

    private static final String URI = "/login/oauth2/success";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        JwtToken token = oAuth2AuthenticationProvider.generateToken(authentication);
        oAuth2AuthenticationProvider.updateRefreshToken(authentication, token);
        DetailLoginAPIResponse detailLoginAPIResponse = DetailLoginAPIResponse.of(authentication, token);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), detailLoginAPIResponse);
    }
}
