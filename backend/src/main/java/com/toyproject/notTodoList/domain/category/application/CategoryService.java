package com.toyproject.notTodoList.domain.category.application;

import com.toyproject.notTodoList.core.properties.ErrorCode;
import com.toyproject.notTodoList.domain.auth.exception.AuthException;
import com.toyproject.notTodoList.domain.category.application.dto.req.CreateCategoryRequest;
import com.toyproject.notTodoList.domain.category.application.dto.req.DeleteCategoryRequest;
import com.toyproject.notTodoList.domain.category.application.dto.req.ReadCategoryRequest;
import com.toyproject.notTodoList.domain.category.application.dto.req.UpdateCategoryRequest;
import com.toyproject.notTodoList.domain.category.application.dto.res.CreateCategoryResponse;
import com.toyproject.notTodoList.domain.category.application.dto.res.DeleteCategoryResponse;
import com.toyproject.notTodoList.domain.category.application.dto.res.ReadCategoryResponse;
import com.toyproject.notTodoList.domain.category.application.dto.res.UpdateCategoryResponse;
import com.toyproject.notTodoList.domain.category.domain.entity.Category;
import com.toyproject.notTodoList.domain.category.domain.vo.CategoryVO;
import com.toyproject.notTodoList.domain.category.exception.CategroyException;
import com.toyproject.notTodoList.domain.category.infrastructure.jdbc.CategoryJdbcTemplateRepository;
import com.toyproject.notTodoList.domain.category.presentation.dto.req.UpdateCategoryAPIRequest;
import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import com.toyproject.notTodoList.domain.member.exception.MemberException;
import com.toyproject.notTodoList.domain.member.infrastructure.jdbc.MemberJdbcTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CategoryService {

    private final MemberJdbcTemplateRepository memberJdbcTemplateRepository;
    private final CategoryJdbcTemplateRepository categoryJdbcTemplateRepository;

    @Transactional
    public CreateCategoryResponse create(CreateCategoryRequest request) {
        Member member = memberJdbcTemplateRepository.readByUsername(request.getUsername())
                .orElseThrow(()-> new CategroyException(ErrorCode.USER_NOT_FOUND));
        Long categoryId = categoryJdbcTemplateRepository.create(member.getId(), request.toCategoryEntity());
        return CreateCategoryResponse.from (categoryId);
    }

    @Transactional
    public UpdateCategoryResponse update(UpdateCategoryRequest request) {
        CategoryVO category = categoryJdbcTemplateRepository.update(request);
        return UpdateCategoryResponse.from (category);
    }

    @Transactional
    public ReadCategoryResponse read(ReadCategoryRequest request) {
        Member member = memberJdbcTemplateRepository.readByUsername(request.getUsername())
                .orElseThrow(()-> new CategroyException(ErrorCode.USER_NOT_FOUND));
        List<Category> categories = categoryJdbcTemplateRepository.readAllByMemberId(member.getId());
        return ReadCategoryResponse.from (categories);
    }

    @Transactional
    public DeleteCategoryResponse delete(DeleteCategoryRequest request) {
        Member member = memberJdbcTemplateRepository.readByUsername(request.getUsername())
                .orElseThrow(()-> new CategroyException(ErrorCode.USER_NOT_FOUND));
        Long categoryId = categoryJdbcTemplateRepository.delete(member.getId(), request.getCategoryId());
        if (categoryId == 0L)
            throw new CategroyException(ErrorCode.USER_CATEGORY_NOT_FOUND);
        return DeleteCategoryResponse.from(request.getCategoryId());
    }


}
