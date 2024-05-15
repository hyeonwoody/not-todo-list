package com.toyproject.notTodoList.entity.universe;

import jakarta.persistence.*;

@Entity
@Table(name = "enum_category", schema = "universe")
public class EnumCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}