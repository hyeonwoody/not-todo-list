package com.toyproject.notTodoList.core.properties;


import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.springframework.http.HttpStatus.*;


@Getter
@AllArgsConstructor
public enum ErrorCode {
    //400
    AUTH_INCORRECT_PASSWORD(BAD_REQUEST, "비밀번호가 일치하지 않습니다"),

    //404
    USER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다"),

    //409
    MEMBER_DUPLICATE_USERNAME(CONFLICT, "이미 존재하는 아이디 입니다."),
    MEMBER_DUPLICATE_NICKNAME(CONFLICT, "이미 존재하는 닉네임 입니다."),

    //500
    REGISTER_MEMBER_ERROR(INTERNAL_SERVER_ERROR, "회원가입에 실패하였습니다.");
    private final HttpStatus httpStatus;
    private final String message;

}