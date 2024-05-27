package com.toyproject.notTodoList.core.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class OAuth2LoginConfig {

    @Value("${my.ipAddress}")
    String ipAddress;
    @Value("${my.OAuth.google.client-id}")
    private String googleClientId;
    @Value("${my.OAuth.google.client-secret}")
    private String googleClientSecret;
    @Value("${my.OAuth.google.redirect-uri}")
    private String googleRedirectUri;
    @Value("${my.OAuth.google.authorization-uri}")
    private String googleAuthorizationUri;
    @Value("${my.OAuth.google.token-uri}")
    private String googleTokenUri;
    @Value("${my.OAuth.google.user-info-uri}")
    private String googleUserInfoUri;
    @Value("${my.OAuth.google.jwk-set-uri}")
    private String googleJwkSetUri;
    @Value("${my.OAuth.google.scope}")
    private String googleScope;


    @Value("${my.OAuth.naver.client-id}")
    private String naverClientId;
    @Value("${my.OAuth.naver.client-secret}")
    private String naverClientSecret;
    @Value("${my.OAuth.naver.redirect-uri}")
    private String naverRedirectUri;
    @Value("${my.OAuth.naver.authorization-uri}")
    private String naverAuthorizationUri;
    @Value("${my.OAuth.naver.token-uri}")
    private String naverTokenUri;
    @Value("${my.OAuth.naver.user-info-uri}")
    private String naverUserInfoUri;
    @Value("${my.OAuth.naver.jwk-set-uri}")
    private String naverJwkSetUri;
    @Value("${my.OAuth.naver.scope}")
    private String naverScope;


    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {

        return new InMemoryClientRegistrationRepository(
                this.googleClientRegistration(),
                this.naverClientRegistration()
        );
    }

//    private ClientRegistration googleCommonClientRegistration() {
//        return CommonOAuth2Provider.GOOGLE.getBuilder("google")
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .redirectUri("http://localhost"+redirectUri)
//                .build();
//    }

    private ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .clientId(googleClientId)
                .clientSecret(googleClientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                //Todo : Setting domain. So that it does not redirect to localhost.
                .redirectUri("http://localhost"+ googleRedirectUri)
                .scope(googleScope.split(",")) //Invalid if the parameter is String type
                .authorizationUri(googleAuthorizationUri)
                .tokenUri(googleTokenUri)
                .userInfoUri(googleUserInfoUri)
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .jwkSetUri(googleJwkSetUri)
                .clientName("Google")
                .build();
    }

    private ClientRegistration naverClientRegistration() {
        return ClientRegistration.withRegistrationId("naver")
                .clientId(naverClientId)
                .clientSecret(naverClientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .redirectUri("http://127.0.0.1"+ naverRedirectUri)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope(naverScope.split(",")) //Invalid if the parameter is String type
                .authorizationUri(naverAuthorizationUri)
                .tokenUri(naverTokenUri)
                .userInfoUri(naverUserInfoUri)
                .userNameAttributeName("response")
                .jwkSetUri(naverJwkSetUri)
                .clientName("Naver")
                .build();
    }
}