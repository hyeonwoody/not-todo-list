package com.toyproject.notTodoList.domain.auth.presentation.dto.req;

import lombok.Getter;

@Getter
public class OAuthAPIRequest {
    private String state;
    private String code;
    private String scope;
    private String authuser;
    private String prompt;

}
