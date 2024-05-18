package com.toyproject.notTodoList.domain.member.exception;

import com.toyproject.notTodoList.core.properties.ErrorCode;
import com.toyproject.notTodoList.exception.CustomException;

public class MemberException extends CustomException {
    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberException(ErrorCode errorCode,
                         String runtimeValue) {
        super(errorCode, runtimeValue);
    }
}
