package com.toyproject.notTodoList.domain.auth.application.dto.req;

import com.toyproject.notTodoList.core.properties.ErrorCode;
import com.toyproject.notTodoList.domain.auth.exception.AuthException;
import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import com.toyproject.notTodoList.domain.member.domain.entity.authprovider.AuthProvider;
import com.toyproject.notTodoList.domain.member.domain.entity.profile.domain.entity.Profile;
import com.toyproject.notTodoList.domain.member.domain.entity.role.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;


@Getter
@Builder
public class OAuth2MemberRequest {

    private String name;
    private String email;
    private String profile;
    private Integer authProvider;

    public static OAuth2MemberRequest of(String registrationId, Map<String, Object> oAuth2UserAttributes) {
        return switch (registrationId){
            case "google" -> ofGoogle(oAuth2UserAttributes);
            default -> throw new AuthException(ErrorCode.ILLEGAL_REGISTRATION_ID);
        };
    }

    private static OAuth2MemberRequest ofGoogle (Map<String, Object> attributes){
        return OAuth2MemberRequest.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profile((String) attributes.get("picture"))
                .authProvider(AuthProvider.GOOGLE.ordinal())
                .build();
    }

    public Member toMemberEntity(Long id) {
        return Member.builder()
                .id(id)
                .username(email)
                .role(Role.MEMBER_BASIC.getValue())
                .authProvider(authProvider)
                .build();
    }

    public Member toMemberEntity() {
        return Member.builder()
                .username(email)
                .role(Role.MEMBER_BASIC.getValue())
                .authProvider(authProvider)
                .build();
    }

    public Profile toProfileEntity() {
        return Profile.builder()
                .nickname(name)
                .build();
    }
}
