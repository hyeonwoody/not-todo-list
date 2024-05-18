package com.toyproject.notTodoList.domain.auth.application.dto.req;

import com.toyproject.notTodoList.domain.auth.presentation.dto.req.RegisterAPIRequest;
import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import com.toyproject.notTodoList.domain.member.domain.entity.password.domain.entity.Password;
import com.toyproject.notTodoList.domain.member.domain.entity.profile.domain.entity.Profile;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterRequest {
    private String username;
    private String password;
    private Integer authProvider;
    private String nickname;

    public static RegisterRequest from(RegisterAPIRequest request) {
        return RegisterRequest.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .authProvider(request.getAuthProvider())
                .nickname(request.getNickname())
                .build();
    }

    public Member toMemberEntity() {
        return Member.builder()
                .username(username)
                .authProvider(authProvider)
                .build();
    }

    public Password toPasswordEntity() {
        return Password.builder()
                .password(password)
                .build();
    }

    public Profile toProfileEntity() {
        return Profile.builder()
                .nick_name(nickname)
                .build();
    }
}
