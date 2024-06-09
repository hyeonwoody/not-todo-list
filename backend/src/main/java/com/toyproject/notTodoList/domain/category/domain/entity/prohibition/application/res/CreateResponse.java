package com.toyproject.notTodoList.domain.category.domain.entity.prohibition.application.res;

import com.toyproject.notTodoList.domain.category.domain.entity.prohibition.domain.entity.Prohibition;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateResponse {
    Long id;

    public static CreateResponse from(Prohibition prohibition) {
        return new CreateResponse(
                prohibition.getId());
    }
}
