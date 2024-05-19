package com.toyproject.notTodoList.core.configuration.security;

import com.toyproject.notTodoList.core.properties.JwtProperties;
import com.toyproject.notTodoList.domain.auth.jwt.JwtAuthenticationProvider;
import com.toyproject.notTodoList.domain.auth.jwt.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.Date;
import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private final JwtProperties jwtProperties;
    @Value("${my.ipAddress}")
    String ipAddress;

    @Value("${my.frontEndPort}")
    String frontEndPort;

//    @Value("${okta.oauth2.issuer}")
//    private String issuer;
//    @Value("${okta.oauth2.client-id}")
//    private String clientId;


    private final ObjectPostProcessor<Object> objectPostProcessor;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())

                //Set as STATELESS since not using sessions
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/auth/register").permitAll() //Register API
                        .requestMatchers("/api/v1/auth/login").permitAll()) //Login API
                .authorizeHttpRequests(authorize->
                        authorize.anyRequest().authenticated()) //Unauthorized access to others is not allowed
//                .oauth2Login(withDefaults())
//                .logout(logout -> logout
//                        .addLogoutHandler(logoutHandler()))
                .build();
    }

//    private LogoutHandler logoutHandler() {
//        return (request, response, authentication) -> {
//            try {
//                String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
//                response.sendRedirect(issuer + "v2/logout?client_id=" + clientId + "&returnTo=" + baseUrl);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        };
//    }

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



    @Bean
    public WebSecurityCustomizer ignoringWebSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**"))
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**"))
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui.html"))
                .requestMatchers(new AntPathRequestMatcher("/swagger-resources/**"));
    }

    @Bean
    public AuthenticationManager authenticationManager(
            JwtAuthenticationProvider jwtAuthenticationProvider) throws Exception {
        return new AuthenticationManagerBuilder(objectPostProcessor)
                .authenticationProvider(jwtAuthenticationProvider).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource(){
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.addAllowedHeader("*");
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
