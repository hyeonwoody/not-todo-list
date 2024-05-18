package com.toyproject.notTodoList.core.properties;


import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ErrorCode {

    //409
    MEMBER_DUPLICATE_USERNAME(CONFLICT, "이미 존재하는 아이디 입니다."),
    MEMBER_DUPLICATE_NICKNAME(CONFLICT, "이미 존재하는 닉네임 입니다."),

    //500
    REGISTER_MEMBER_ERROR(INTERNAL_SERVER_ERROR, "회원가입에 실패하였습니다.");
    private final HttpStatus httpStatus;
    private final String message;

}