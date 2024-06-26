package com.toyproject.notTodoList.domain.member.domain.entity.password.infrastructure.jdbc;

import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import com.toyproject.notTodoList.domain.member.domain.entity.password.domain.entity.Password;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class PasswordJdbcTemplateRepository {

    private JdbcTemplate jdbcTemplate;

    public PasswordJdbcTemplateRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long create (Long memberId, Password password)
    {
        String sql = "INSERT INTO password (member_id, password) VALUE (?, ?)";
        Timestamp now = new Timestamp(System.currentTimeMillis());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setLong(1, memberId);
            ps.setString(2, password.getPassword());
            return ps;
        }, keyHolder);
        if (rowsAffected == 1) {
            return keyHolder.getKey().longValue();
        } else {
            return 0L;
        }
    }

    public Optional<Password> readByMemberId(Long memberId) {
        String sql = "SELECT password FROM password WHERE member_id = ? LIMIT 1";
        List<Password> password = jdbcTemplate.query(sql, (rs, rowNum) -> Password.builder()
                        .password(rs.getString("password"))
                        .build()
                , memberId);
        return password.stream().findFirst();
    }
}

