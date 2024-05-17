package com.toyproject.notTodoList.domain.member.application.vo;

import com.toyproject.notTodoList.domain.member.domain.entity.Category;
import com.toyproject.notTodoList.domain.member.domain.entity.Profile;
import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberDTO {
    private Long id;



    public MemberDTO (String username, Member.AuthProvider authProvider)
    {
        this.username = username;
        this.authProvider = authProvider;
    }
    public MemberDTO(long id, Object authProvider) {
        this.id = id;
        this.authProvider = (Member.AuthProvider) authProvider;
    }
    private String username;
    private Member.AuthProvider authProvider;

    private String password;

    private Profile profile;
    private List<Category> categories;
}
