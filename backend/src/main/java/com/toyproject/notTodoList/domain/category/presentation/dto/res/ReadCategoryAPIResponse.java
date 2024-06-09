package com.toyproject.notTodoList.domain.category.presentation.dto.res;

import com.toyproject.notTodoList.domain.category.application.dto.res.ReadCategoryResponse;
import com.toyproject.notTodoList.domain.category.domain.vo.CategoryVO;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadCategoryAPIResponse {



    private List<CategoryVO> categories;

    public static ReadCategoryAPIResponse from(ReadCategoryResponse response) {
        List<CategoryVO> categoryDTOList = response.getCategories().stream()
                .map(category -> new CategoryVO(category.getId(), category.getName()))
                .collect(Collectors.toList());
        return new ReadCategoryAPIResponse(categoryDTOList);
    }
}
