
package com.toyproject.notTodoList.domain.member.domain.entity.memberAuth;

import com.toyproject.notTodoList.domain.auth.domain.entity.Auth;
import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import jakarta.persistence.*;

@Entity
@Table(name = "member_auth", schema = "universe")
public class MemberAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "auth_id")
    private Auth auth;
    private boolean enabled;
}