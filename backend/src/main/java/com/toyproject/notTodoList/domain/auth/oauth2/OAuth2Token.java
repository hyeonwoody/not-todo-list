package com.toyproject.notTodoList.domain.auth.oauth2;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class OAuth2Token {
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private Date refreshTokenExpiryDate;
}
