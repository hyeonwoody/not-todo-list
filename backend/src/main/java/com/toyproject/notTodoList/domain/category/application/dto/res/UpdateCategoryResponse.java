package com.toyproject.notTodoList.domain.category.application.dto.res;

import com.toyproject.notTodoList.domain.category.domain.vo.CategoryVO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateCategoryResponse {
    private CategoryVO category;
    public static UpdateCategoryResponse from (CategoryVO category)
    {
        return new UpdateCategoryResponse(category);
    }
}
