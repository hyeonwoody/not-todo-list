package com.toyproject.notTodoList.domain.category.domain.entity.prohibition.presentation.dto.req;

import lombok.Getter;

@Getter
public class CreateAPIRequest {
    private String category;
    private String content;
    private Integer scope;
}
