package com.example.talisman;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

/**
 * Created by gipotalamus on 07.05.16.
 */
@Configuration
public class JpaConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/test?useUnicode=yes&characterEncoding=UTF-8") /* mysql:3306 */
                .driverClassName("com.mysql.jdbc.Driver")
                .username("root")
                .password("root")
                .build();
    }
}
