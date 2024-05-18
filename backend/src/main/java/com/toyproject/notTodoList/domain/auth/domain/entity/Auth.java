package com.toyproject.notTodoList.domain.auth.domain.entity;


import com.toyproject.notTodoList.domain.member.domain.entity.password.domain.entity.Password;
import com.toyproject.notTodoList.domain.member.domain.entity.profile.domain.entity.Profile;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "auth", schema = "universe")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
