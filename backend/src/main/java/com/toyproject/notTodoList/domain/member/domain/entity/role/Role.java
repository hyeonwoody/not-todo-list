package com.toyproject.notTodoList.domain.member.domain.entity.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN("ROLE_ADMIN", 0),
    GUEST("ROLE_GUEST", 1),
    MEMBER_BASIC("ROLE_MEMBER_BASIC", 2),
    MEMBER_ELITE("ROLE_MEMBER_PRO_PLUS", 4),
    MEMBER_PRO("ROLE_MEMBER_PRO",  3),
    MEMBER_VIP("ROLE_MEMBER_VIP", 4);


    private final String key;
    private final Integer value;

    public static Optional<Integer> getValueByKey(String key) {
        return Arrays.stream(Role.values())
                .filter(role -> role.getKey().equals(key))
                .map(Role::getValue)
                .findFirst();
    }
}