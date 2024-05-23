package com.toyproject.notTodoList.domain.member.application.service;

import com.toyproject.notTodoList.core.properties.ErrorCode;
import com.toyproject.notTodoList.domain.auth.exception.AuthException;
import com.toyproject.notTodoList.domain.member.exception.MemberException;
import com.toyproject.notTodoList.domain.member.infrastructure.jdbc.MemberJdbcTemplateRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private MemberJdbcTemplateRepository memberJdbcTemplateRepository;

    public MemberService (MemberJdbcTemplateRepository memberJdbcTemplateRepository)
    {
        this.memberJdbcTemplateRepository = memberJdbcTemplateRepository;
    }


}