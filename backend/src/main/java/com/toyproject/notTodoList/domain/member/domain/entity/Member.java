package com.toyproject.notTodoList.domain.member.domain.entity;


import com.toyproject.notTodoList.domain.member.domain.entity.password.domain.entity.Password;
import com.toyproject.notTodoList.domain.member.domain.entity.profile.domain.entity.Profile;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "member", schema = "user")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private String username;


    private Integer authProvider;

    private Date createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Password password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Profile profile;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Category> categories;

    public void setId(Long memberId) {
    }

    public enum AuthProvider {
        EXPLORE,
        LOCAL,
        GOOGLE
    }
}
