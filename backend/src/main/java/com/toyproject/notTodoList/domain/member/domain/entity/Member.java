package com.toyproject.notTodoList.domain.member.domain.entity;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "member", schema = "user")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private String username;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private Date createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Password password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Profile profile;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Category> categories;

    public enum AuthProvider {
        EXPLORE,
        LOCAL,
        GOOGLE
    }
}
