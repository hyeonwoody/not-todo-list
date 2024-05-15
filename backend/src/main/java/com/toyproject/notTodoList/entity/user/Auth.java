package com.toyproject.notTodoList.entity.user;


import jakarta.persistence.*;

@Entity
@Table(name = "auth", schema = "user")
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}