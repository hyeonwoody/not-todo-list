package com.toyproject.notTodoList.domain.category.application.dto.req;

import com.toyproject.notTodoList.domain.category.domain.entity.Category;
import com.toyproject.notTodoList.domain.category.presentation.dto.req.CreateCategoryAPIRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCategoryRequest {
    private String username;
    private String categoryName;

    public static CreateCategoryRequest from(CreateCategoryAPIRequest request) {
        return new CreateCategoryRequest(request.getUsername(), request.getCategoryName());
    }


    public Category toCategoryEntity() {
        return Category.builder()
                .name(categoryName)
                .build();
    }

    public static CreateCategoryRequest of(String principal, CreateCategoryAPIRequest request) {
        return new CreateCategoryRequest(principal, request.getCategoryName());
    }
}
