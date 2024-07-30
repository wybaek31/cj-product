package com.cj.product.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing
@EntityScan(basePackages = {"com.cj.product.core.domain"})
@EnableJpaRepositories(basePackages = {"com.cj.product.core.repository"})
public class DataSourceConfig {

    private final Environment env;

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(Objects.requireNonNull(env.getProperty("spring.datasource.driver-class-name"), "Driver class name is required"));
        dataSourceBuilder.url(Objects.requireNonNull(env.getProperty("spring.datasource.url"), "URL is required"));
        dataSourceBuilder.username(Objects.requireNonNull(env.getProperty("spring.datasource.username"), "Username is required"));
        dataSourceBuilder.password(Objects.requireNonNull(env.getProperty("spring.datasource.password"), "Password is required"));
        return dataSourceBuilder.build();
    }

}