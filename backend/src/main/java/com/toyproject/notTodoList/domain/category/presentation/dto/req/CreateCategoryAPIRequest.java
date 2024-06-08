package com.toyproject.notTodoList.domain.category.presentation.dto.req;

import lombok.Getter;

@Getter
public class CreateCategoryAPIRequest {
    private String username;
    private String categoryName;
}
