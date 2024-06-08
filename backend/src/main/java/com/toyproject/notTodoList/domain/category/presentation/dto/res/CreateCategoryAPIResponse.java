package com.toyproject.notTodoList.domain.category.presentation.dto.res;

import com.toyproject.notTodoList.domain.category.application.dto.res.CreateCategoryResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor (access = AccessLevel.PRIVATE)
public class CreateCategoryAPIResponse {
    Long id;
    public static CreateCategoryAPIResponse from(CreateCategoryResponse response) {
        return new CreateCategoryAPIResponse(response.getId());
    }
}
