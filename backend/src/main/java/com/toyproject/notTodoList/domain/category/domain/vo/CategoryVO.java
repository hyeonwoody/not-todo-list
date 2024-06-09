package com.toyproject.notTodoList.domain.category.domain.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoryVO{
    private Long id;
    private String name;
}