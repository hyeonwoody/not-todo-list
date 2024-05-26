package com.toyproject.notTodoList.domain.auth.application.dto.res;

import com.toyproject.notTodoList.domain.member.domain.entity.memberAuth.MemberAuth;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class OAuth2MemberResponse {
    private Long id;
    private String username;
    private String nickname;
    private Integer role;
    private List<MemberAuth> auths;
    private List<GrantedAuthority> authorities;
}
