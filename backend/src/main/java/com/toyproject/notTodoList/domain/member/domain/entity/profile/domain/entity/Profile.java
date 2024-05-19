package com.toyproject.notTodoList.domain.member.domain.entity.profile.domain.entity;

import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "profile", schema = "user")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column (name = "nick_name")
    private String nickname;

    private Integer status;

    private Date last_login;

}
