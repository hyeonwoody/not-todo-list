package com.toyproject.notTodoList.domain.member.domain.entity.profile.infrastructure;

import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import com.toyproject.notTodoList.domain.member.domain.entity.password.domain.entity.Password;
import com.toyproject.notTodoList.domain.member.domain.entity.profile.domain.entity.Profile;
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
            ps.setString(2, profile.getNickname());
            return ps;
        }, keyHolder);
        if (rowsAffected == 1) {
            return keyHolder.getKey().longValue();
        } else {
            return 0L;
        }
    }

    public Optional<Profile> readByMemberId(Long memberId) {
        String sql = "SELECT nick_name FROM profile WHERE member_id = ? LIMIT 1";
        List<Profile> profile = jdbcTemplate.query(sql, (rs, rowNum) -> Profile.builder()
                        .nickname(rs.getString("nick_name"))
                        .build()
                , memberId);
        return profile.stream().findFirst();
    }

    public Optional<Profile> readByNickname(String nickname)
    {
        String sql = "SELECT id FROM profile WHERE nickname = ? LIMIT 1";
        List<Profile> profile = jdbcTemplate.query(sql, (rs, rowNum) -> Profile.builder()
                        .id(rs.getLong("id"))
                        .nickname(nickname)
                        .build()
                , nickname);
        return profile.stream().findFirst();
    }
}
