package com.toyproject.notTodoList.domain.auth.domain.entity.authority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Authority {
    LOGIN,
    CREATE_CATEGORY_5,
    CREATE_PROHIBITATION_10,
    CREATE_CATEGORY_10,
    CREATE_PROHIBITATION_20,
}
