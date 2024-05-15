package com.toyproject.notTodoList.entity.user;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "profile", schema = "user")  // Use schema name if required
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String nick_name;

    private Date last_login;

}
