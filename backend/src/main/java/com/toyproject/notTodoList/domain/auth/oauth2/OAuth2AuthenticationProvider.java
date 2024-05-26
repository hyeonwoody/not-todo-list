package com.toyproject.notTodoList.domain.auth.oauth2;

import com.toyproject.notTodoList.core.security.PrincipalDetails;
import com.toyproject.notTodoList.domain.auth.application.AuthService;
import com.toyproject.notTodoList.domain.auth.jwt.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationProvider implements AuthenticationProvider {

    private final OAuth2Helper OAuth2Helper;
    private final AuthService authService;


    public JwtToken generateToken(Authentication authentication) {
        JwtToken token = getToken(authentication);
        return token;
    }

    private JwtToken getToken(Authentication authentication) {
        String[] roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);
        if (authentication.getPrincipal() instanceof PrincipalDetails){
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            return OAuth2Helper.sign (principalDetails.getMemberId(), roles);
        }
        else {
            return null;
        }
    }

    public void updateRefreshToken(Authentication authentication, JwtToken token) {
        if (authentication.getPrincipal() instanceof PrincipalDetails){
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            authService.updateRefreshToken(principalDetails.getLoginResponse(), token);
        }

    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
