package com.toyproject.notTodoList.domain.member.domain.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "prohibitation", schema = "universe")
public class Prohibitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;
    private Date updated_at;
    private Date created_at;



}
