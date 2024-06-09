package com.toyproject.notTodoList.domain.category.presentation.dto.req;

import lombok.Getter;

@Getter
public class DeleteCategoryAPIRequest {
    private String username;
    private Long categoryId;
}
