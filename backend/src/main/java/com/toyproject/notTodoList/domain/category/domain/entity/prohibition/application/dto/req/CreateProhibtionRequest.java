package com.toyproject.notTodoList.domain.category.domain.entity.prohibition.application.dto.req;

import com.toyproject.notTodoList.core.security.PrincipalDetails;

import com.toyproject.notTodoList.domain.category.domain.entity.prohibition.domain.entity.Prohibition;
import com.toyproject.notTodoList.domain.category.domain.entity.prohibition.presentation.dto.req.CreateAPIRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateProhibtionRequest {

    private String username;
    private String category;
    private String content;
    private Integer scope;

    public static CreateProhibtionRequest from(CreateAPIRequest request, String principal) {

        return CreateProhibtionRequest.builder()
                .username(principal)
                .category(request.getCategory())
                .content(request.getContent())
                .scope(request.getScope())
                .build();
    }

    public static CreateProhibtionRequest from(CreateAPIRequest request, PrincipalDetails principalDetails) {

        return CreateProhibtionRequest.builder()
                .username(principalDetails.getUsername())
                .category(request.getCategory())
                .content(request.getContent())
                .scope(request.getScope())
                .build();
    }

    public static CreateProhibtionRequest from(CreateAPIRequest request) {
        return CreateProhibtionRequest.builder()
                .category(request.getCategory())
                .content(request.getContent())
                .scope(request.getScope())
                .build();
    }

    public Prohibition toProhibitionEntity() {
        return Prohibition.builder()
                .content(content)
                .scope(scope)
                .build();
    }
}
