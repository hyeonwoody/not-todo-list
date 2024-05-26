package com.toyproject.notTodoList.core.security;

import com.toyproject.notTodoList.domain.auth.oauth2.OAuth2AuthenticationProvider;

import com.toyproject.notTodoList.domain.auth.jwt.JwtToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;


@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {


    private final OAuth2AuthenticationProvider OAuth2AuthenticationProvider;

    private static final String URI = "/login/oauth2/success";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        JwtToken token = OAuth2AuthenticationProvider.generateToken(authentication);
        OAuth2AuthenticationProvider.updateRefreshToken(authentication, token);

        String redirectUrl = UriComponentsBuilder.fromUriString(URI)
                .queryParam("accessToken", token.getAccessToken())
                .build().toUriString();
        System.out.println(redirectUrl);
        response.sendRedirect(redirectUrl);
    }
}
