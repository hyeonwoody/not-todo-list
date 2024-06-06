package com.toyproject.notTodoList.domain.auth.application.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class JwtFilterRequest {
    private String authorization;
    private String refreshToken;
    private String updateRefreshToken;
    private Date updatedExpiryDate;
    private String username;
}
