package com.toyproject.notTodoList.domain.member.domain.entity;


import com.toyproject.notTodoList.domain.member.domain.entity.role.Role;
import com.toyproject.notTodoList.domain.member.domain.entity.password.domain.entity.Password;
import com.toyproject.notTodoList.domain.member.domain.entity.profile.domain.entity.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


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

    @Column (nullable = false)
    private String username;

    @Column (nullable = false)
    private Integer authProvider;

    @Column (nullable = false)
    private Integer role;

    @Column
    private Date createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Password password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Profile profile;

    @ManyToMany(mappedBy = "members") //mappedBy refers to the field in Course
    private Set<Category> categories;

    public List<GrantedAuthority> getAuthorities() {
        return Stream.of(new SimpleGrantedAuthority(Role.values()[role].getKey()))
                .map(GrantedAuthority.class::cast)
                .toList();
    }

}
