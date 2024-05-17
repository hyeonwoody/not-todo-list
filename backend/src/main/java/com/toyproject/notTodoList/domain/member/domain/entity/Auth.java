package com.toyproject.notTodoList.domain.member.domain.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "auth", schema = "user")
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}