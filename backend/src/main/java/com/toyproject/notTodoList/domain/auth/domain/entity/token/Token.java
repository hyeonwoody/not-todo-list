package com.toyproject.notTodoList.domain.auth.domain.entity.token;

import com.toyproject.notTodoList.domain.member.domain.entity.memberAuth.MemberAuth;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "token", schema = "universe")
@Getter

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accessToken;
    private String refreshToken;
    private Date refreshTokenExpiryDate;

    @OneToOne (orphanRemoval = true)
    @JoinColumn (name = "member_auth_id")
    private MemberAuth memberAuth;

    @Builder
    private Token (Long id, String accessToken, String refreshToken, Date refreshTokenExpiryDate, MemberAuth memberAuth){
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiryDate = refreshTokenExpiryDate;
        this.memberAuth = memberAuth;
    }

    @Builder
    private Token (Long id, String refreshToken, Date refreshTokenExpiryDate, MemberAuth memberAuth){
        this.id = id;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiryDate = refreshTokenExpiryDate;
        this.memberAuth = memberAuth;
    }

    public void updateRefreshToken(String refreshToken, Date refreshTokenExpiryDate){
        this.refreshToken = refreshToken;
        this.refreshTokenExpiryDate = refreshTokenExpiryDate;
    }
}
