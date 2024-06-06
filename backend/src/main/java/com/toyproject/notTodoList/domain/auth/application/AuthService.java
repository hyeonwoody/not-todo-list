package com.toyproject.notTodoList.domain.auth.application;

import com.toyproject.notTodoList.core.properties.ErrorCode;
import com.toyproject.notTodoList.domain.auth.application.dto.req.JwtFilterRequest;
import com.toyproject.notTodoList.domain.auth.application.dto.res.LoginResponse;
import com.toyproject.notTodoList.domain.auth.application.dto.res.RegisterResponse;
import com.toyproject.notTodoList.domain.auth.application.dto.req.RegisterRequest;
import com.toyproject.notTodoList.domain.auth.domain.entity.token.Token;
import com.toyproject.notTodoList.domain.auth.domain.entity.token.infrastructure.TokenJdbcTemplateRepository;
import com.toyproject.notTodoList.domain.auth.exception.AuthException;
import com.toyproject.notTodoList.domain.auth.jwt.JwtToken;
import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import com.toyproject.notTodoList.domain.member.domain.entity.memberAuth.MemberAuth;
import com.toyproject.notTodoList.domain.member.domain.entity.memberAuth.infrastructure.MemberAuthJdbcTemplateRepository;
import com.toyproject.notTodoList.domain.member.domain.entity.password.domain.entity.Password;
import com.toyproject.notTodoList.domain.member.domain.entity.password.infrastructure.jdbc.PasswordJdbcTemplateRepository;
import com.toyproject.notTodoList.domain.member.domain.entity.profile.domain.entity.Profile;
import com.toyproject.notTodoList.domain.member.domain.entity.profile.infrastructure.ProfileJdbcTemplateRepository;
import com.toyproject.notTodoList.domain.member.infrastructure.jdbc.MemberJdbcTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static com.auth0.jwt.JWT.create;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final MemberJdbcTemplateRepository memberJdbcTemplateRepository;
    private final PasswordJdbcTemplateRepository passwordJdbcTemplateRepository;
    private final ProfileJdbcTemplateRepository profileJdbcTemplateRepository;

    private final MemberAuthJdbcTemplateRepository memberAuthJdbcTemplateRepository;
    private final TokenJdbcTemplateRepository tokenJdbcTemplateRepository;

    public RegisterResponse register (RegisterRequest request) {
        isUsernameTaken(request.getUsername());
        Long memberId = memberJdbcTemplateRepository.create(request.toMemberEntity());
        if (memberId == 0L)
            throw new AuthException(ErrorCode.REGISTER_MEMBER_ERROR);
        Long passwordId = passwordJdbcTemplateRepository.create(memberId, request.toPasswordEntity(passwordEncoder));
        Long profileId = profileJdbcTemplateRepository.create(memberId, request.toProfileEntity());
        Long memberAuthId = memberJdbcTemplateRepository.giveBasicPermission(memberId);
        return RegisterResponse.from(memberAuthId);
    }

    //Todo : Improve concurrency with Thread
    @Transactional(readOnly = true)
    public LoginResponse login(String username, String password) {
        Member member = memberJdbcTemplateRepository.readByUsername(username)
                .orElseThrow(() ->
            new AuthException(ErrorCode.USER_NOT_FOUND));
        Password credential = passwordJdbcTemplateRepository.readByMemberId(member.getId())
                .orElseThrow(() ->
            new AuthException(ErrorCode.USER_NOT_FOUND));
        credential.verifyPassword(passwordEncoder ,password);
        Profile profile = profileJdbcTemplateRepository.readByMemberId(member.getId())
                .orElseThrow(() ->
            new AuthException(ErrorCode.USER_NOT_FOUND));
        List<MemberAuth> auths = memberJdbcTemplateRepository.getPermission(member.getId());
        return LoginResponse.from(member, profile, auths);
    }

    @Transactional(readOnly = true)
    public void isUsernameTaken (String username) {
        memberJdbcTemplateRepository.readByUsername(username).ifPresent(auth -> {
            throw new AuthException(ErrorCode.MEMBER_DUPLICATE_USERNAME);
        });
    }

    public void updateRefreshToken(JwtFilterRequest request){
        Member member = memberJdbcTemplateRepository.readByUsername (request.getUsername())
                .orElseThrow(()-> new AuthException(ErrorCode.USER_NOT_FOUND));
        MemberAuth memberAuth = memberAuthJdbcTemplateRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));
        Optional<Token> optionalToken = tokenJdbcTemplateRepository.readByMemberAuthId(memberAuth.getId());
        if (optionalToken.isPresent()){
            Token token = optionalToken.get();
            token.updateRefreshToken(request.getUpdateRefreshToken(), request.getUpdatedExpiryDate());
            tokenJdbcTemplateRepository.update(token);
        }
    }

    public void updateRefreshToken(LoginResponse response, JwtToken jwttoken) {
        Long authId = response.getAuths().get(0).getId();
        Optional<Token> optionalToken = tokenJdbcTemplateRepository.readByMemberAuthId(authId);

        if (optionalToken.isPresent()) {
            Token token = optionalToken.get();
            token.updateRefreshToken(jwttoken.getRefreshToken(), jwttoken.getRefreshTokenExpiryDate());
            tokenJdbcTemplateRepository.update(token);
        } else {
            MemberAuth memberAuth = memberAuthJdbcTemplateRepository.findByMemberAuthId(authId)
                    .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));
            tokenJdbcTemplateRepository.create(Token.builder()
                    .memberAuth(memberAuth)
                    .accessToken(jwttoken.getAccessToken())
                    .refreshToken(jwttoken.getRefreshToken())
                    .refreshTokenExpiryDate(jwttoken.getRefreshTokenExpiryDate())
                    .build());
        }
    }
}
