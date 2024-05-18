package com.toyproject.notTodoList.domain.member.domain.entity.profile.infrastructure;

import com.toyproject.notTodoList.domain.member.domain.entity.profile.domain.entity.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

@Repository
public class ProfileJdbcTemplateRepository {

    private JdbcTemplate jdbcTemplate;

    public ProfileJdbcTemplateRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long create (Long memberId, Profile profile)
    {
        String sql = "INSERT INTO profile (member_id, nick_name) VALUE (?, ?)";
        Timestamp now = new Timestamp(System.currentTimeMillis());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setLong(1, memberId);
            ps.setString(2, profile.getNick_name());
            return ps;
        }, keyHolder);
        if (rowsAffected == 1) {
            return keyHolder.getKey().longValue();
        } else {
            return 0L;
        }
    }
}
