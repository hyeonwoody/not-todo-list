package com.toyproject.notTodoList.domain.category.presentation.dto.res;

import com.toyproject.notTodoList.domain.category.application.dto.res.CreateCategoryResponse;
import com.toyproject.notTodoList.domain.category.application.dto.res.DeleteCategoryResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteCategoryAPIResponse {
    Long id;
    public static DeleteCategoryAPIResponse from(DeleteCategoryResponse response) {
        return new DeleteCategoryAPIResponse(response.getId());
    }
}