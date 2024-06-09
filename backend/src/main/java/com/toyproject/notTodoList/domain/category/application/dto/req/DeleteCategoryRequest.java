package com.toyproject.notTodoList.domain.category.application.dto.req;

import com.toyproject.notTodoList.domain.category.presentation.dto.req.CreateCategoryAPIRequest;
import com.toyproject.notTodoList.domain.category.presentation.dto.req.DeleteCategoryAPIRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor (access = AccessLevel.PRIVATE)
public class DeleteCategoryRequest {
    private String username;
    private Long categoryId;


    public static DeleteCategoryRequest from(DeleteCategoryAPIRequest request) {
        return new DeleteCategoryRequest(request.getUsername(), request.getCategoryId());
    }

    public static DeleteCategoryRequest of(String principal, Long categoryId) {
        return new DeleteCategoryRequest(principal, categoryId);
    }
}
