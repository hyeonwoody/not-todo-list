package com.toyproject.notTodoList.domain.member.application.service;

import com.toyproject.notTodoList.domain.member.application.vo.MemberDTO;
import com.toyproject.notTodoList.domain.member.infrastructure.jdbc.MemberJdbcTemplateRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private MemberJdbcTemplateRepository memberJdbcTemplateRepository;

    public MemberService (MemberJdbcTemplateRepository memberJdbcTemplateRepository)
    {
        this.memberJdbcTemplateRepository = memberJdbcTemplateRepository;
    }

    public Long registerMember (MemberDTO member)
    {
        Long id = memberJdbcTemplateRepository.create(member);
        return id;
    }

    public boolean isUsernameTaken (String username)
    {
        MemberDTO member = memberJdbcTemplateRepository.readByUserName(username);
        return member != null;
    }
}