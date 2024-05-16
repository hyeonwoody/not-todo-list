package com.toyproject.notTodoList.repository.jdbc;

import com.toyproject.notTodoList.DTO.member.MemberDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

@Repository
public class JdbcTemplateMemberRepository {
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository (DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long create (MemberDTO member)
    {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO member (username, auth_provider, created_at) VALUE (?, ?, ?)";
        Timestamp now = new Timestamp(System.currentTimeMillis());

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, member.getUsername());
                ps.setString(2, member.getAuthProvider().toString());
                ps.setTimestamp(3, now);
                return ps;
        }, keyHolder);
        Long userId = (Long) keyHolder.getKey().longValue();
        return userId;
    }
    }
