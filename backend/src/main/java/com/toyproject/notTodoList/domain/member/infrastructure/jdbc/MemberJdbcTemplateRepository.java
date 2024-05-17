package com.toyproject.notTodoList.domain.member.infrastructure.jdbc;

import com.toyproject.notTodoList.domain.member.application.vo.MemberDTO;
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
public class MemberJdbcTemplateRepository {
    private JdbcTemplate jdbcTemplate;

    public MemberJdbcTemplateRepository(DataSource dataSource){
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

    public MemberDTO readById (long id)
    {
        String sql = "SELECT id, auth_provider, created_at FROM member WHERE id = ? LIMIT 1";

        List<MemberDTO> retMember = jdbcTemplate.query(sql, (rs, rowNum) -> new MemberDTO(rs.getLong("id"),
                        Member.AuthProvider.valueOf(rs.getString("auth_provider")))
                , id);

        if (retMember.isEmpty()) {
            return null;
        } else {
            return retMember.get(0);
        }
    }

    public MemberDTO readByUserName(String username) {
        String sql = "SELECT id, auth_provider, created_at FROM member WHERE username = ? LIMIT 1";

        List<MemberDTO> retMember = jdbcTemplate.query(sql, (rs, rowNum) -> new MemberDTO(rs.getLong("id"),
                        Member.AuthProvider.valueOf(rs.getString("auth_provider")))
                , username);

        if (retMember.isEmpty()) {
            return null;
        } else {
            return retMember.get(0);
        }
    }

    public boolean update (MemberDTO member)
    {
        String sql = "UPDATE member SET auth_provider = ? WHERE id = ?";
        jdbcTemplate.update(sql, new Object[]{member.getAuthProvider().toString(), member.getId()});
        return true;
    }

    public boolean delete (long id)
    {
        String sql = "DELETE FROM member WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return true;
    }


}
