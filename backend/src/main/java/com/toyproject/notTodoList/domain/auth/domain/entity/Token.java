package com.toyproject.notTodoList.domain.auth.domain.entity;

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

    private String refreshToken;
    private Date refreshTokenExpiryDate;

    @OneToOne (optional = false, orphanRemoval = true)
    @JoinColumn (name = "auth_id")
    private MemberAuth auth;

    @Builder
    private Token (String refreshToken, Date refreshTokenExpiryDate, MemberAuth auth){
        this.refreshToken = refreshToken;
        this.refreshTokenExpiryDate = refreshTokenExpiryDate;
        this.auth = auth;
    }

    public void updateRefreshToken(String refreshToken, Date refreshTokenExpiryDate){
        this.refreshToken = refreshToken;
        this.refreshTokenExpiryDate = refreshTokenExpiryDate;
    }
}
