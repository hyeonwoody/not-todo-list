package com.toyproject.notTodoList.domain.auth.google;

import com.toyproject.notTodoList.core.security.PrincipalDetails;
import com.toyproject.notTodoList.domain.auth.application.AuthService;
import com.toyproject.notTodoList.domain.auth.application.dto.res.LoginResponse;
import com.toyproject.notTodoList.domain.auth.domain.entity.Auth;
import com.toyproject.notTodoList.domain.auth.jwt.JwtAuthenticationContext;
import com.toyproject.notTodoList.domain.auth.jwt.JwtHelper;
import com.toyproject.notTodoList.domain.auth.jwt.JwtToken;
import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class GoogleAuthenticationProvider {

    private final GoogleHelper googleHelper;
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
            return googleHelper.sign (principalDetails.getMemberId(), roles);
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
}
