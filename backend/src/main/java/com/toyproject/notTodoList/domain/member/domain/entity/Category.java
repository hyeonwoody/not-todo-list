package com.toyproject.notTodoList.domain.member.domain.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "category", schema = "user")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "member_categories", // Name of the junction table
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Member> members;
}
