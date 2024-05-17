package com.toyproject.notTodoList.domain.member.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "category", schema = "user")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    private String name;
}
