package com.toyproject.notTodoList.domain.member.domain.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "enum_auth", schema = "universe")
public class EnumAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}