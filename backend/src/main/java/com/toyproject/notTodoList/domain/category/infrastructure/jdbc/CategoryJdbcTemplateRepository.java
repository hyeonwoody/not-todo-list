package com.toyproject.notTodoList.domain.category.infrastructure.jdbc;

import com.toyproject.notTodoList.domain.category.application.dto.req.CreateCategoryRequest;
import com.toyproject.notTodoList.domain.category.application.dto.req.DeleteCategoryRequest;
import com.toyproject.notTodoList.domain.category.application.dto.req.ReadCategoryRequest;
import com.toyproject.notTodoList.domain.category.application.dto.req.UpdateCategoryRequest;
import com.toyproject.notTodoList.domain.category.domain.entity.Category;
import com.toyproject.notTodoList.domain.category.domain.vo.CategoryVO;
import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class CategoryJdbcTemplateRepository {

    private JdbcTemplate jdbcTemplate;

    public CategoryJdbcTemplateRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long create(Long memberId, Category category) {
        String record = "INSERT INTO category (name) VALUE (?)";
        Timestamp now = new Timestamp(System.currentTimeMillis());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(record, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, category.getName());
                return ps;
            }, keyHolder);

        if (rowsAffected == 1) {
            Long categoryId = keyHolder.getKey().longValue();
            String junction = "INSERT INTO member_categories (member_id, category_id) VALUES (?, ?)";
            rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(junction, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setLong(1, memberId);
                ps.setLong(2, categoryId);
                return ps;
            }, keyHolder);
            if (rowsAffected == 1)
                return categoryId;
        }
        return 0L;
    }

    public List<Category> readAllByMemberId(Long memberId) {
        String sql = "SELECT c.id, c.name FROM category c JOIN member_categories mc ON c.id = mc.category_id WHERE mc.member_id = ?";
        return jdbcTemplate.query(sql, new Object[]{memberId}, (rs, rowNum) ->
                Category.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .build()
        );
    }

    public CategoryVO update(UpdateCategoryRequest request) {
        String sql = "UPDATE category SET name = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, request.getUpdatedCategoryName(), request.getCategoryId());
        if (rowsAffected == 0){
            return null;
        }
        CategoryVO updatedCategory = new CategoryVO(request.getCategoryId(), request.getUpdatedCategoryName());
        return updatedCategory;
    }

    public Long delete(Long memberId, Long categoryId) {
        String junction  = "DELETE FROM member_categories WHERE member_id = ? AND category_id = ?";
        Timestamp now = new Timestamp(System.currentTimeMillis());
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(junction , PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setLong(1, memberId);
            ps.setLong(2, categoryId);
            return ps;
        });

        if (rowsAffected == 1) {
            String record = "DELETE FROM category WHERE id = ?";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(record, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setLong(1, categoryId);
                return ps;
            }, keyHolder);
            if (rowsAffected == 1)
                return categoryId;
        }
        return 0L;
    }
}
