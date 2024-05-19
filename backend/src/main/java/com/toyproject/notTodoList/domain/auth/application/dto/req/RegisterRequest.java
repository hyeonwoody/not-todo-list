package com.toyproject.notTodoList.domain.auth.application.dto.req;

import com.toyproject.notTodoList.domain.auth.presentation.dto.req.RegisterAPIRequest;
import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import com.toyproject.notTodoList.domain.member.domain.entity.password.domain.entity.Password;
import com.toyproject.notTodoList.domain.member.domain.entity.profile.domain.entity.Profile;
import lombok.Builder;
import lombok.Getter;

import org.springframework.security.crypto.password.PasswordEncoder;

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
                .role(1)
                .authProvider(authProvider)
                .build();
    }

    public Password toPasswordEntity(PasswordEncoder passwordEncoder) {
        return Password.builder()
                .password(passwordEncoder.encode(password))
                .build();
    }

    public Profile toProfileEntity() {
        return Profile.builder()
                .nickname(nickname)
                .build();
    }
}
