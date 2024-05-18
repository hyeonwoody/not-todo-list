package com.toyproject.notTodoList.exception;

import com.toyproject.notTodoList.exception.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler (value ={CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException (CustomException e, HttpServletRequest request){
        return ErrorResponse.toResponseEntity(e.getErrorCode(), e.getRuntimeValue());
    }
}
