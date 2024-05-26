package com.toyproject.notTodoList.domain.auth.google;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class GoogleToken {
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private Date refreshTokenExpiryDate;
}
