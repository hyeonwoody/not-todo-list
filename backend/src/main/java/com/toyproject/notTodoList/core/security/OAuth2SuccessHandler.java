package com.toyproject.notTodoList.core.security;

import com.toyproject.notTodoList.domain.auth.google.GoogleAuthenticationProvider;

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


    private final GoogleAuthenticationProvider googleAuthenticationProvider;

    private static final String URI = "/login/oauth2/success";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        JwtToken token = googleAuthenticationProvider.generateToken(authentication);
        googleAuthenticationProvider.updateRefreshToken(authentication, token);

        String redirectUrl = UriComponentsBuilder.fromUriString(URI)
                .queryParam("accessToken", token.getAccessToken())
                .build().toUriString();
        System.out.println(redirectUrl);
        response.sendRedirect(redirectUrl);
    }
}
