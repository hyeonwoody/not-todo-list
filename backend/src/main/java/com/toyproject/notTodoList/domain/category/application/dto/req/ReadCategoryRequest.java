package com.toyproject.notTodoList.domain.category.application.dto.req;

import com.toyproject.notTodoList.domain.category.presentation.dto.req.DeleteCategoryAPIRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadCategoryRequest {
    private String username;

    public static ReadCategoryRequest from (String username){
        return new ReadCategoryRequest(username);
    }
}