package com.toyproject.notTodoList.domain.category.infrastructure.jdbc;

import com.toyproject.notTodoList.domain.category.application.dto.req.CreateCategoryRequest;
import com.toyproject.notTodoList.domain.category.domain.entity.Category;
import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

@Repository
public class CategoryJdbcTemplateRepository {

    private JdbcTemplate jdbcTemplate;

    public CategoryJdbcTemplateRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long create(Category category) {
        String sql = "INSERT INTO category (name) VALUE (?)";
        Timestamp now = new Timestamp(System.currentTimeMillis());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, category.getName());
            return ps;
        }, keyHolder);
        if (rowsAffected == 1) {
            return keyHolder.getKey().longValue();
        } else {
            return 0L;
        }
    }
}
