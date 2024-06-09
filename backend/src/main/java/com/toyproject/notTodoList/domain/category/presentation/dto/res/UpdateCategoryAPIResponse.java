package com.toyproject.notTodoList.domain.category.presentation.dto.res;

import com.toyproject.notTodoList.domain.category.application.dto.res.UpdateCategoryResponse;
import com.toyproject.notTodoList.domain.category.domain.vo.CategoryVO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateCategoryAPIResponse {
    private CategoryVO category;

    public static UpdateCategoryAPIResponse from(UpdateCategoryResponse response) {
        return new UpdateCategoryAPIResponse(response.getCategory());
    }
}
