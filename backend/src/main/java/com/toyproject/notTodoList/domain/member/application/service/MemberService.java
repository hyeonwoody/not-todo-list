package com.toyproject.notTodoList.domain.member.application.service;

import com.toyproject.notTodoList.core.properties.ErrorCode;
import com.toyproject.notTodoList.domain.auth.exception.AuthException;
import com.toyproject.notTodoList.domain.member.exception.MemberException;
import com.toyproject.notTodoList.domain.member.infrastructure.jdbc.MemberJdbcTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberJdbcTemplateRepository memberJdbcTemplateRepository;

    public String findUsernameById(Long extractedId) {
        return memberJdbcTemplateRepository.findUsernameById(extractedId);
    }
}