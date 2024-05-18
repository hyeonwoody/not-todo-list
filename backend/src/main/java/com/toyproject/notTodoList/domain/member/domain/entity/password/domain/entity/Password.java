package com.toyproject.notTodoList.domain.member.domain.entity.password.domain.entity;

import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "password", schema = "user")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
}