package com.toyproject.notTodoList.domain.auth.presentation.dto.res;

import com.toyproject.notTodoList.domain.auth.application.dto.res.LoginResponse;
import com.toyproject.notTodoList.domain.auth.domain.entity.Auth;
import com.toyproject.notTodoList.domain.auth.jwt.JwtToken;
import com.toyproject.notTodoList.domain.member.domain.entity.role.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Optional;

@Getter
@AllArgsConstructor (access = AccessLevel.PRIVATE)
public class DetailLoginAPIResponse {

    private String accessToken;
    private String refreshToken;
    private Integer role;

    public static DetailLoginAPIResponse of(Authentication authentication, JwtToken token) {
        return new DetailLoginAPIResponse(
                token.getAccessToken(),
                token.getRefreshToken(),
                authentication.getAuthorities().stream().findFirst()
                        .map(GrantedAuthority::getAuthority)
                        .flatMap(Role::getValueByKey)
                        .orElse(2)
        );
    }

    public static DetailLoginAPIResponse of(LoginResponse response, JwtToken token) {
        return new DetailLoginAPIResponse(
                token.getAccessToken(),
                token.getRefreshToken(),
                response.getRole()
        );
    }
}
