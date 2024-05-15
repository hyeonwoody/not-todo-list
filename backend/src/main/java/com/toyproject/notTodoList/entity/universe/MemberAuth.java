
package com.toyproject.notTodoList.entity.universe;

import com.toyproject.notTodoList.entity.user.Auth;
import com.toyproject.notTodoList.entity.user.Member;
import jakarta.persistence.*;

@Entity
@Table(name = "member_auth", schema = "universe")
public class MemberAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;  // Assuming "member" refers to User

    @ManyToOne
    @JoinColumn(name = "auth_id")
    private Auth auth;

    private boolean enabled;
}