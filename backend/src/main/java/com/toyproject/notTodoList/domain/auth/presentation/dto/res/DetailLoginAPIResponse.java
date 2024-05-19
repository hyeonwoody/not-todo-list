package com.toyproject.notTodoList.domain.auth.presentation.dto.res;

import com.toyproject.notTodoList.domain.auth.application.dto.res.LoginResponse;
import com.toyproject.notTodoList.domain.auth.jwt.JwtToken;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor (access = AccessLevel.PRIVATE)
public class DetailLoginAPIResponse {

    private String accessToken;
    private String refreshToken;
    private String refreshTokenExpiryDate;
    private Integer role;

    public static DetailLoginAPIResponse of(LoginResponse response, JwtToken authentication) {
        return new DetailLoginAPIResponse(
                authentication.getAccessToken(),
                authentication.getRefreshToken(),
                authentication.getRefreshTokenExpiryDate().toString(),
                response.getRole()
        );
    }
}
