package com.toyproject.notTodoList.domain.auth.exception;

import com.toyproject.notTodoList.core.properties.ErrorCode;
import com.toyproject.notTodoList.exception.CustomException;

public class AuthException extends CustomException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthException(ErrorCode errorCode,
                         String runtimeValue) {
        super(errorCode, runtimeValue);
    }
}