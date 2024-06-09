package com.toyproject.notTodoList.domain.category.application.dto.res;

import com.toyproject.notTodoList.domain.category.domain.entity.Category;
import com.toyproject.notTodoList.domain.category.domain.vo.CategoryVO;
import com.toyproject.notTodoList.domain.category.presentation.dto.res.ReadCategoryAPIResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadCategoryResponse {
    private List<CategoryVO> categories;
    public static ReadCategoryResponse from (List<Category> categories)
    {
        List<CategoryVO> categoryDTOList = categories.stream()
                .map(category -> new CategoryVO(category.getId(), category.getName()))
                .collect(Collectors.toList());
        return new ReadCategoryResponse(categoryDTOList);
    }
}