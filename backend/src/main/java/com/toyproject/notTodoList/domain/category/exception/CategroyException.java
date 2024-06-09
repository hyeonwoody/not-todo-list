package com.toyproject.notTodoList.domain.category.exception;

import com.toyproject.notTodoList.core.properties.ErrorCode;
import com.toyproject.notTodoList.exception.CustomException;

public class CategroyException extends CustomException {
    public CategroyException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CategroyException(ErrorCode errorCode,
                                String runtimeValue) {
        super(errorCode, runtimeValue);
    }
}
