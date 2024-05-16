package com.toyproject.notTodoList.repository.jdbc;

import com.toyproject.notTodoList.DTO.member.MemberDTO;
import com.toyproject.notTodoList.configuration.jdbc.JdbcConfig;
import com.toyproject.notTodoList.entity.user.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.util.ReflectionTestUtils;


@DisplayName("회원")
@SpringBootTest
class JdbcTemplateMemberRepositoryTest {
    private JdbcTemplate jdbcTemplate;

    private JdbcTemplateMemberRepository repository;

    @Autowired
    private JdbcConfig jdbcConfig;

    String drvierClassName;
    String username;
    String password;
    String url;
    public JdbcTemplateMemberRepositoryTest(){
        this.drvierClassName = "{MY_DATABASE_DRIVERCLASSNAME}";
        this.username = "{MY_DATABASE_USERNAME}";
        this.password = "{MY_DATABASE_PASSWORD}";
        this.url = "{MY_DATABASE_URL}";

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(drvierClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        repository = new JdbcTemplateMemberRepository(dataSource);
    }

    @Test
    void create() {
        Long userId = repository.create(new MemberDTO("fdff", Member.AuthProvider.LOCAL));

        assertEquals(2, userId);

    }
}