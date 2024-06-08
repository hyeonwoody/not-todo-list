package com.toyproject.notTodoList.domain.category.application.dto.res;

import com.toyproject.notTodoList.domain.auth.application.dto.res.RegisterResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCategoryResponse {
    Long id;
    public static CreateCategoryResponse from (Long authId)
    {
        return new CreateCategoryResponse(authId);
    }
}
