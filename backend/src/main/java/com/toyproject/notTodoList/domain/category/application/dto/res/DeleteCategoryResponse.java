package com.toyproject.notTodoList.domain.category.application.dto.res;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteCategoryResponse {
    Long id;
    public static DeleteCategoryResponse from (Long authId)
    {
        return new DeleteCategoryResponse(authId);
    }
}