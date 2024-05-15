package com.toyproject.notTodoList.entity.user;

import jakarta.persistence.*;

@Entity
@Table(name = "password", schema = "user")
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
}