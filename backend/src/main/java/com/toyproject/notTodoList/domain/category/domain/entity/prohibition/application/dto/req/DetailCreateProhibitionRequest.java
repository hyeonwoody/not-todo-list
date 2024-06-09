package com.toyproject.notTodoList.domain.category.domain.entity.prohibition.application.dto.req;

import com.toyproject.notTodoList.domain.category.domain.entity.Category;
import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import com.toyproject.notTodoList.domain.category.domain.entity.prohibition.domain.entity.Prohibition;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DetailCreateProhibitionRequest {

    private Member member;
    private Category category;
    private String content;
    private Integer scope;

    public static DetailCreateProhibitionRequest of(CreateProhibtionRequest request, Member member, Category category) {
        return new DetailCreateProhibitionRequest(
                member,
                category,
                request.getContent(),
                request.getScope()
        );
    }

    public Prohibition toProhibitionEntity() {
        return Prohibition.builder()
                .member(member)
                .category(category)
                .content(content)
                .scope(scope)
                .build();
    }
}
