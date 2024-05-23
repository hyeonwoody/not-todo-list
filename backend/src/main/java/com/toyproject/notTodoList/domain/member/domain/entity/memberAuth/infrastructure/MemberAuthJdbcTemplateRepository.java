package com.toyproject.notTodoList.domain.member.domain.entity.memberAuth.infrastructure;

import com.toyproject.notTodoList.domain.auth.domain.entity.Auth;
import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import com.toyproject.notTodoList.domain.member.domain.entity.memberAuth.MemberAuth;
import com.toyproject.notTodoList.domain.member.domain.entity.password.domain.entity.Password;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

    //    public List<Auth> getPermission(Long memberId) {
//        String sql =    "SELECT a.name " +
//                        "FROM auth a " +
//                        "JOIN member_auth ma ON a.id = ma.auth_id " +
//                        "JOIN member m ON ma.member_id = m.id " +
//                        "WHERE m.id = ?";
//        return jdbcTemplate.query(sql, (rs, rowNum)-> Auth.builder()
//                .name(rs.getString("name"))
//                .build(), memberId);
//    }

    public Optional <MemberAuth> findByMemberAuthId(Long id) {
        String sql = "SELECT id FROM member_auth WHERE id = ?";
        List<MemberAuth> auth = jdbcTemplate.query(sql, (rs, rowNum) -> MemberAuth.builder()
                        .id(rs.getLong("id"))
                        .build()
                , id);
        return auth.stream().findFirst();
    }


}
