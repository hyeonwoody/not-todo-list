package com.toyproject.notTodoList.domain.auth.jwt;

import com.toyproject.notTodoList.domain.auth.application.AuthService;
import com.toyproject.notTodoList.domain.auth.application.dto.res.LoginResponse;
import com.toyproject.notTodoList.domain.auth.domain.entity.token.application.TokenService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtHelper jwtHelper;
    private final AuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationContext jwtAuthenticationContext = (JwtAuthenticationContext) authentication;
        return processMemberAuthentication(String.valueOf(jwtAuthenticationContext.getPrincipal()),
                jwtAuthenticationContext.getCredentials());
    }

    private Authentication processMemberAuthentication(String principal, String credentials) {
        try {
            LoginResponse response = authService.login(principal, credentials);
            List<GrantedAuthority> authorities = response.getAuthorities();
            JwtToken token = getToken (response.getId(), authorities);
            JwtAuthenticationContext authenticated = new JwtAuthenticationContext(token, null, authorities);
            authenticated.setDetails(response);
            authService.updateRefreshToken(response, token);
            return authenticated;
        }
        catch (IllegalArgumentException e){
            throw new BadCredentialsException(e.getMessage());
        }
        catch (DataAccessException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

    private JwtToken getToken(Long userId, List<GrantedAuthority> authorities) {
        String[] roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);
        return jwtHelper.sign(userId, roles);
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationContext.class.isAssignableFrom(authentication));
    }
}
