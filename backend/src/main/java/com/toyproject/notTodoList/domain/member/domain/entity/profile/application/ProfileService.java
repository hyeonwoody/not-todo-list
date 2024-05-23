package com.toyproject.notTodoList.domain.member.domain.entity.profile.application;

import com.toyproject.notTodoList.core.properties.ErrorCode;
import com.toyproject.notTodoList.domain.member.domain.entity.profile.domain.entity.Profile;
import com.toyproject.notTodoList.domain.member.domain.entity.profile.infrastructure.ProfileJdbcTemplateRepository;
import com.toyproject.notTodoList.domain.member.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

    private final ProfileJdbcTemplateRepository profileJdbcTemplateRepository;

    public void isNicknameTaken(String nickname) {
        profileJdbcTemplateRepository.readByNickname(nickname).ifPresent(auth -> {
            throw new MemberException(ErrorCode.MEMBER_DUPLICATE_NICKNAME);
        });
    }
}
