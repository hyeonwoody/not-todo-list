package com.toyproject.notTodoList.DTO.member;

import com.toyproject.notTodoList.entity.user.Member;
import lombok.Data;
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
    private String username;
    private Member.AuthProvider authProvider;

    private String password;

    private ProfileDTO profile;
    private List<CategoryDTO> categories;


}
