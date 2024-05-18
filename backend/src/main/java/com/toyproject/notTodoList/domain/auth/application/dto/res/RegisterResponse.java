package com.toyproject.notTodoList.domain.auth.application.dto.res;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterResponse {

    Long id;
    public static RegisterResponse from (Long authId)
    {
        return new RegisterResponse(authId);
    }
}
