package com.toyproject.notTodoList.domain.auth.application;

import com.toyproject.notTodoList.core.properties.ErrorCode;
import com.toyproject.notTodoList.core.security.PrincipalDetails;
import com.toyproject.notTodoList.domain.auth.application.dto.req.OAuth2MemberRequest;
import com.toyproject.notTodoList.domain.auth.application.dto.res.LoginResponse;
import com.toyproject.notTodoList.domain.auth.exception.AuthException;
import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import com.toyproject.notTodoList.domain.member.domain.entity.memberAuth.MemberAuth;
import com.toyproject.notTodoList.domain.member.domain.entity.profile.domain.entity.Profile;
import com.toyproject.notTodoList.domain.member.domain.entity.profile.infrastructure.ProfileJdbcTemplateRepository;
import com.toyproject.notTodoList.domain.member.infrastructure.jdbc.MemberJdbcTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberJdbcTemplateRepository memberJdbcTemplateRepository;
    private final ProfileJdbcTemplateRepository profileJdbcTemplateRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. 유저 정보(attributes) 가져오기
        Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();

        // 2. resistrationId 가져오기 (third-party id)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 3. userNameAttributeName 가져오기
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // 4. 유저 정보 dto 생성
        OAuth2MemberRequest oAuth2MemberRequest = OAuth2MemberRequest.of(registrationId, oAuth2UserAttributes);

        // 5. 회원가입 및 로그인
        LoginResponse member = getOrSaveOauth(oAuth2MemberRequest);

        // 6. OAuth2User로 반환
        return new PrincipalDetails(member, oAuth2UserAttributes, userNameAttributeName);
    }


    private LoginResponse getOrSaveOauth(OAuth2MemberRequest request) {
        Member member = memberJdbcTemplateRepository.readByEmail(request.getAuthProvider(), request.getEmail())
                .orElseGet(()->{
                    Long memberId = memberJdbcTemplateRepository.create(request.toMemberEntity());
                    profileJdbcTemplateRepository.create(memberId, request.toProfileEntity());
                    memberJdbcTemplateRepository.giveBasicPermission(memberId);
                    return request.toMemberEntity(memberId);
                    });
        Profile profile = profileJdbcTemplateRepository.readByMemberId(member.getId())
                .orElseThrow(() ->
                        new AuthException(ErrorCode.USER_NOT_FOUND));
        List<MemberAuth> auths = memberJdbcTemplateRepository.getPermission(member.getId());
        return LoginResponse.from(member, profile, auths);
    }
}
