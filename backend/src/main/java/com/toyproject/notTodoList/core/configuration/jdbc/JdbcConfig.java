package com.toyproject.notTodoList.core.configuration.jdbc;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

    @Getter
    @Value("${my.database.driverClassName}")
    String drvierClassName;

    @Getter
    @Value("${my.database.username}")
    String username;

    @Getter
    @Value("${my.database.password}")
    String password;

    @Getter
    @Value("${my.database.url}")
    String url;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(drvierClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}