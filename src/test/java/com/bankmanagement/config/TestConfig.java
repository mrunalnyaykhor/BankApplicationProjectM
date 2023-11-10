package com.bankmanagement.config;

// TestConfig.java
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        // Configure H2 in-memory database
        return new org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2)
                .build();
    }
}
