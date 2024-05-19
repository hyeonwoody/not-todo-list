package com.toyproject.notTodoList.domain.auth.domain.entity.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    MEMBER("ROLE_MEMBER", "멤버"),
    ADMIN("ROLE_ADMIN", "어드민");

    private final String key;
    private final String title;
}