package com.toyproject.notTodoList.domain.member.infrastructure.jdbc;

import com.toyproject.notTodoList.domain.member.domain.entity.Member;
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
public class MemberJdbcTemplateRepository {
    private JdbcTemplate jdbcTemplate;

    public MemberJdbcTemplateRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long create (Member member)
    {
        String sql = "INSERT INTO member (username, auth_provider, role, created_at) VALUE (?, ?, ?, ?)";
        Timestamp now = new Timestamp(System.currentTimeMillis());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, member.getUsername());
                ps.setString(2, member.getAuthProvider().toString());
                ps.setInt(3, member.getRole());
                ps.setTimestamp(4, now);
                return ps;
        }, keyHolder);
        if (rowsAffected == 1) {
            return keyHolder.getKey().longValue();
        } else {
            return 0L;
        }
    }

    public Optional<Member> readById (long id)
    {
        String sql = "SELECT id, auth_provider, created_at FROM member WHERE id = ? LIMIT 1";
        List<Member> member = jdbcTemplate.query(sql, (rs, rowNum) -> Member.builder()
                .id(rs.getLong("id"))
                .authProvider(rs.getInt("auth_provider"))
                .build()
                , id);
        return member.stream().findFirst();
    }

    public Optional<Member> readByUsername(String username)
    {
        String sql = "SELECT id, auth_provider, role, created_at FROM member WHERE username = ? LIMIT 1";
        List<Member> retMember = jdbcTemplate.query(sql, (rs, rowNum) -> Member.builder()
                .id(rs.getLong("id"))
                .username(username)
                .authProvider(rs.getInt("auth_provider"))
                .role(rs.getInt("role"))
                .build()
                , username);
        return retMember.stream().findFirst();
    }

    public int update (Member member)
    {
        String sql = "UPDATE member SET auth_provider = ? WHERE id = ?";
        return jdbcTemplate.update(sql, new Object[]{member.getAuthProvider().toString(), member.getId()});
    }

    public int delete (long id)
    {
        String sql = "DELETE FROM member WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
