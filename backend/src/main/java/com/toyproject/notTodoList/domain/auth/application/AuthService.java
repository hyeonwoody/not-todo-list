package com.toyproject.notTodoList.domain.auth.application;

import com.toyproject.notTodoList.core.properties.ErrorCode;
import com.toyproject.notTodoList.domain.auth.application.dto.res.RegisterResponse;
import com.toyproject.notTodoList.domain.auth.application.dto.req.RegisterRequest;
import com.toyproject.notTodoList.domain.auth.exception.AuthException;
import com.toyproject.notTodoList.domain.member.domain.entity.memberAuth.infrastructure.MemberAuthJdbcTemplateRepository;
import com.toyproject.notTodoList.domain.member.domain.entity.password.infrastructure.jdbc.PasswordJdbcTemplateRepository;
import com.toyproject.notTodoList.domain.member.domain.entity.profile.infrastructure.ProfileJdbcTemplateRepository;
import com.toyproject.notTodoList.domain.member.infrastructure.jdbc.MemberJdbcTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberJdbcTemplateRepository memberJdbcTemplateRepository;
    private final PasswordJdbcTemplateRepository passwordJdbcTemplateRepository;
    private final ProfileJdbcTemplateRepository profileJdbcTemplateRepository;

    private final MemberAuthJdbcTemplateRepository memberAuthJdbcTemplateRepository;

    @Transactional(readOnly = true)
    public RegisterResponse register (RegisterRequest request) {
        isUsernameTaken(request.getUsername());
        Long memberId = memberJdbcTemplateRepository.create(request.toMemberEntity());
        if (memberId == 0L)
            throw new AuthException(ErrorCode.REGISTER_MEMBER_ERROR);
        Long passwordId = passwordJdbcTemplateRepository.create(memberId, request.toPasswordEntity());
        Long profileId = profileJdbcTemplateRepository.create(memberId, request.toProfileEntity());
        Long memberAuthId = memberAuthJdbcTemplateRepository.giveBasicPermission(memberId);
        return RegisterResponse.from(memberAuthId);
    }

    @Transactional(readOnly = true)
    public void isUsernameTaken (String username) {
        memberJdbcTemplateRepository.readByUsername(username).ifPresent(auth -> {
            throw new AuthException(ErrorCode.MEMBER_DUPLICATE_USERNAME);
        });
    }
}
