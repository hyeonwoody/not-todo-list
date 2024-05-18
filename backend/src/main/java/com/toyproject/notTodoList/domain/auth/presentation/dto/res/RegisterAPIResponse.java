package com.toyproject.notTodoList.domain.auth.presentation.dto.res;

import com.toyproject.notTodoList.domain.auth.application.dto.res.RegisterResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor (access = AccessLevel.PRIVATE)
public class RegisterAPIResponse {
    Long id;
    public static RegisterAPIResponse from(RegisterResponse response) {
        return new RegisterAPIResponse(
                response.getId());
    }
}
