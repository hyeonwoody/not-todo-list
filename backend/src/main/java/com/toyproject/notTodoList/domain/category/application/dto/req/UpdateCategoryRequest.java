package com.toyproject.notTodoList.domain.category.application.dto.req;

import com.toyproject.notTodoList.domain.category.presentation.dto.req.UpdateCategoryAPIRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor (access = AccessLevel.PRIVATE)
public class UpdateCategoryRequest {
    private String username;
    private Long categoryId;
    private String updatedCategoryName;

    public static UpdateCategoryRequest of(Long categoryId, UpdateCategoryAPIRequest request) {
        return new UpdateCategoryRequest(request.getUsername(), categoryId, request.getUpdatedCategoryName());
    }
}
