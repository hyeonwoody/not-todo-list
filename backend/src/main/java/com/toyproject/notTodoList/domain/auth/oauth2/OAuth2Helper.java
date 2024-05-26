package com.toyproject.notTodoList.domain.auth.oauth2;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.toyproject.notTodoList.core.properties.GoogleProperties;
import com.toyproject.notTodoList.domain.auth.jwt.JwtToken;
import com.toyproject.notTodoList.util.DateStrategy;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.auth0.jwt.JWT.create;
import static com.auth0.jwt.JWT.require;

@Component
public class OAuth2Helper {

    private static final String USERID_STR = "userId";
    private static final String ROLES_STR = "roles";


    private static final Long SECRET_CONVERTION = 3600003L;

    private final String issuer;
    private final long accessTokenExpirySeconds;
    private final long refreshTokenExpirySeconds;
    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;
    private final DateStrategy dateStrategy;

    private GoogleProperties googleProperties;

    public OAuth2Helper(GoogleProperties googleProperties){
        this.issuer = googleProperties.getIssuer();
        this.accessTokenExpirySeconds = hourToMillis(googleProperties.getAccessTokenExpiryHour());
        this.refreshTokenExpirySeconds = hourToMillis(googleProperties.getRefreshTokenExpiryHour());
        this.algorithm = Algorithm.HMAC512(googleProperties.getClientSecret());
        this.jwtVerifier = require(algorithm)
                .withIssuer(googleProperties.getIssuer())
                .build();
        this.dateStrategy = Date::new;
    }


    private static Long hourToMillis(int hour) {
        return hour * SECRET_CONVERTION;
    }

    private static Date calculateExpirySeconds(Date nowDate, Long tokenExpirySeconds) {
        return new Date(nowDate.getTime() + tokenExpirySeconds);
    }

    public JwtToken sign(Long userId, String[] roles) {
        Date nowDate = dateStrategy.create();
        String accessToken = create()
                .withIssuer(issuer)
                .withIssuedAt(nowDate)
                .withExpiresAt(calculateExpirySeconds(nowDate, accessTokenExpirySeconds))
                .withClaim(USERID_STR, userId)
                .withArrayClaim(ROLES_STR, roles)
                .sign(algorithm);
        String refreshToken = create()
                .withIssuer(issuer)
                .withIssuedAt(nowDate)
                .withExpiresAt(calculateExpirySeconds(nowDate, refreshTokenExpirySeconds))
                .sign(algorithm);
        return JwtToken.builder()
                .userId(userId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpiryDate(calculateExpirySeconds(nowDate, refreshTokenExpirySeconds))
                .build();
    }
}
