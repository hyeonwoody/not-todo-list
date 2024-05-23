package com.toyproject.notTodoList.domain.member.infrastructure.jdbc;

import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import com.toyproject.notTodoList.domain.member.domain.entity.memberAuth.MemberAuth;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public Optional<Member> readByUsername(String username) {
        String sql = "SELECT id, auth_provider, created_at, role FROM member WHERE username = ? LIMIT 1";
        List<Member> member = jdbcTemplate.query(sql, (rs, rowNum) -> Member.builder()
                .id(rs.getLong("id"))
                .authProvider(rs.getInt("auth_provider"))
                .role(rs.getInt("role"))
                .createdAt(rs.getDate("created_at"))
                .build()
                , username);
        return member.stream().findFirst();
    }

    public Optional<Member> readById(long id) {
        String sql = "SELECT id, auth_provider, created_at, username, role FROM member WHERE id = ?";
        try (PreparedStatement ps = jdbcTemplate.getDataSource().getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Member member = Member.builder()
                        .id(rs.getLong("id"))
                        .authProvider(rs.getInt("auth_provider"))
                        .createdAt(rs.getDate("created_at"))
                        .username(rs.getString("username"))
                        .role(rs.getInt("role"))
                        .build();
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            // Handle SQL exceptions appropriately
            throw new RuntimeException(e); // Or a more specific exception type
        }
    }
//
//    public Optional<Member> _readById(long id) {
//        String sql = "SELECT id, auth_provider, created_at, username, role FROM member WHERE id = ? LIMIT 1";
//        try {
//            Member member = jdbcTemplate.queryForObject(
//                    sql,
//                    new Object[]{id},
//                    (rs, rowNum) -> Member.builder()
//                            .id(rs.getLong("id"))
//                            .authProvider(rs.getInt("auth_provider"))
//                            .createdAt(rs.getDate("created_at"))
//                            .username(rs.getString("username"))
//                            .role(rs.getInt("role"))
//                            .build()
//            );
//            return Optional.ofNullable(member);
//        } catch (EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
//    }
//
//    public Optional<Member> readById (long id)
//    {
//        String sql = "SELECT id, auth_provider, created_at, username, role FROM member WHERE id = ? LIMIT 1";
//        List<Member> member = jdbcTemplate.query(sql, (rs, rowNum) -> Member.builder()
//                .id(rs.getLong("id"))
//                .authProvider(rs.getInt("auth_provider"))
//                .createdAt(rs.getDate("created_at"))
//                .username(rs.getString("username"))
//                .role(rs.getInt("role"))
//                .build()
//                , id);
//        return member.stream().findFirst();
//    }



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

    public List<MemberAuth> getPermission(Long memberId) {
        String sql = "SELECT id FROM member_auth WHERE member_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum)-> MemberAuth.builder()
                .id(rs.getLong("id"))
                .build(), memberId);
    }


}
