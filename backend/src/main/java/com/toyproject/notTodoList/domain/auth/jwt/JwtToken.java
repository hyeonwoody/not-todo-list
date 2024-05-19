package com.toyproject.notTodoList.domain.auth.jwt;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class JwtToken {
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private Date refreshTokenExpiryDate;
}
