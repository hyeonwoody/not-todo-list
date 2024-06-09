package com.toyproject.notTodoList.domain.category.domain.entity.prohibition.presentation.dto.res;

import com.toyproject.notTodoList.domain.category.domain.entity.prohibition.application.res.CreateResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateAPIResponse {

    Long id;

    public static CreateAPIResponse from(CreateResponse response) {
        return new CreateAPIResponse(
                response.getId());
    }
}
