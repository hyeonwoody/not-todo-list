package com.toyproject.notTodoList.domain.category.application;

import com.toyproject.notTodoList.domain.category.application.dto.req.CreateCategoryRequest;
import com.toyproject.notTodoList.domain.category.application.dto.res.CreateCategoryResponse;
import com.toyproject.notTodoList.domain.category.infrastructure.jdbc.CategoryJdbcTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {


    private final CategoryJdbcTemplateRepository categoryJdbcTemplateRepository;

    public CreateCategoryResponse create(CreateCategoryRequest request) {
        Long categoryId = categoryJdbcTemplateRepository.create(request.toCategoryEntity());
        return CreateCategoryResponse.from (categoryId);
    }
}
