package com.toyproject.notTodoList.core.configuration.security;

import com.toyproject.notTodoList.core.security.OAuth2SuccessHandler;
import com.toyproject.notTodoList.domain.auth.application.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.beans.factory.annotation.Value;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Value("${my.ipAddress}")
    String ipAddress;

    @Value("${my.frontEndPort}")
    String frontEndPort;

    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                //.cors(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)


                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS)) //Set as STATELESS since not using sessions
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/auth/register").permitAll() //Register API
                        .requestMatchers("/api/v1/auth/login").permitAll()//Login API
//                        .requestMatchers("/login/oauth2/callback/**").permitAll()
                        .anyRequest().authenticated() //Unauthorized access to others is not allowed
                )

                .oauth2Login(oauth -> oauth
                        //.authorizationEndpoint(endpoint -> endpoint.baseUri("/api/v1/auth/oauth2"))
                        .redirectionEndpoint(endpoint -> endpoint.baseUri("/login/oauth2/callback/*"))
                        .userInfoEndpoint(endpoint -> endpoint.userService(oAuth2UserService))
                        .successHandler(oAuth2SuccessHandler)
                        )

                //.httpBasic(withDefaults())


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
    public WebSecurityCustomizer ignoringWebSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**"))
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**"))
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui.html"))

                .requestMatchers(new AntPathRequestMatcher("/swagger-resources/**"))
                .requestMatchers(new AntPathRequestMatcher("/index.html"))
                //.requestMatchers(new AntPathRequestMatcher("/login/oauth2/**"))
                ;
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
