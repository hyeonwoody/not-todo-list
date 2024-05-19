package com.toyproject.notTodoList.domain.auth.domain.entity.token.application;

import com.toyproject.notTodoList.domain.auth.domain.entity.token.Token;
import com.toyproject.notTodoList.domain.auth.domain.entity.token.infrastructure.TokenJdbcTemplateRepository;
import com.toyproject.notTodoList.domain.auth.jwt.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {

    private final TokenJdbcTemplateRepository tokenJdbcTemplateRepository;


}
