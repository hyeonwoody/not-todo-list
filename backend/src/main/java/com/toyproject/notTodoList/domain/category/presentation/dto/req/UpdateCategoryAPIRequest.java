package com.toyproject.notTodoList.domain.category.presentation.dto.req;

import lombok.Getter;

@Getter
public class UpdateCategoryAPIRequest {
    private String username;
    private String updatedCategoryName;
}
