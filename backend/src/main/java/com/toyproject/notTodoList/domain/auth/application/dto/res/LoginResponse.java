package com.toyproject.notTodoList.domain.auth.application.dto.res;

import com.toyproject.notTodoList.domain.auth.domain.entity.Auth;
import com.toyproject.notTodoList.domain.auth.domain.entity.authority.Authority;
import com.toyproject.notTodoList.domain.auth.domain.entity.role.Role;
import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import com.toyproject.notTodoList.domain.member.domain.entity.memberAuth.MemberAuth;
import com.toyproject.notTodoList.domain.member.domain.entity.password.domain.entity.Password;
import com.toyproject.notTodoList.domain.member.domain.entity.profile.domain.entity.Profile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
@AllArgsConstructor (access = AccessLevel.PRIVATE)
public class LoginResponse {

    private Long id;
    private String username;
    private String nickname;
    private Integer role;
    private List<MemberAuth> auths;
    private List<GrantedAuthority> authorities;

    public static LoginResponse from(Member member, Profile profile, List<MemberAuth> auths) {
        return new LoginResponse(
                member.getId(),
                member.getUsername(),
                profile.getNickname(),
                member.getRole(),
                auths,
                member.getAuthorities()
        );
    }
}
