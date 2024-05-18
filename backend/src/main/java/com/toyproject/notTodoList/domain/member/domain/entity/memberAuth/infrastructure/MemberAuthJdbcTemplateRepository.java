package com.toyproject.notTodoList.domain.member.domain.entity.memberAuth.infrastructure;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class MemberAuthJdbcTemplateRepository {

    private JdbcTemplate jdbcTemplate;

    public MemberAuthJdbcTemplateRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public Long giveBasicPermission(Long memberId) {
        String sql = "INSERT INTO member_auth (enabled, member_id, auth_id) VALUE (?, ?, ?)";
        Long[] permissions = {1L, 2L, 3L}; //createCategory, createProhibitation
        int[] rowsAffected = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setBoolean(1, true);
                ps.setLong(2, memberId);
                ps.setLong(3, permissions[i]);
            }

            @Override
            public int getBatchSize() {
                return permissions.length;
            }
        });
        if (rowsAffected.length == permissions.length) {
            return memberId;
        } else {
            return 0L;
        }
    }
}
