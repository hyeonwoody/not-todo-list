package com.toyproject.notTodoList.domain.auth.jwt;

import static com.auth0.jwt.JWT.create;
import static com.auth0.jwt.JWT.require;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.toyproject.notTodoList.util.DateStrategy;


import java.util.Date;
import java.util.Map;

public class JwtHelper {
    private static final String USERID_STR = "userId";
    private static final String ROLES_STR = "roles";

    private static final Long SECRET_CONVERTION = 3600001L;

    private final String issuer;
    private final long accessTokenExpirySeconds;
    private final long refreshTokenExpirySeconds;
    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;
    private final DateStrategy dateStrategy;

    public JwtHelper(String issuer, String clientSecret, int accessTokenExpiryHour,
                     int refreshTokenExpiryHour, DateStrategy dateStrategy) {
        this.issuer = issuer;
        this.accessTokenExpirySeconds = hourToMillis(accessTokenExpiryHour);
        this.refreshTokenExpirySeconds = hourToMillis(refreshTokenExpiryHour);
        this.algorithm = Algorithm.HMAC512(clientSecret);
        this.jwtVerifier = require(algorithm)
                .withIssuer(issuer)
                .build();
        this.dateStrategy = dateStrategy;
    }

    private static Long hourToMillis(int hour) {
        return hour * SECRET_CONVERTION;
    }

    public Map<String, Claim> verify(String token) throws JWTVerificationException {
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getClaims();
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

    public Long extractUserId(String token){
        return JWT.decode(token).getClaim("userId").asLong();
    }

    public String extractUsername(String token){
        return JWT.decode(token).getSubject();
    }

    public Date extractExpiration(String token){
        return JWT.decode(token).getExpiresAt();
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, String username){
        final String extractUsername = extractUsername(token);
        return (extractUsername.equals(username) && !isTokenExpired(token));
    }

    public String updateRefreshToken() {
        Date nowDate = dateStrategy.create();
        String refreshToken = create()
                .withIssuer(issuer)
                .withIssuedAt(nowDate)
                .withExpiresAt(calculateExpirySeconds(nowDate, refreshTokenExpirySeconds))
                .sign(algorithm);
        return refreshToken;
    }
}
