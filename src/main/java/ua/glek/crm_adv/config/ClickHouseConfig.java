package ua.glek.crm_adv.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class ClickHouseConfig {
    @Value("${clickhouse.url}")
    private String url;
    @Value("${clickhouse.user}")
    private String user;
    @Value("${clickhouse.password}")
    private String password;
    @Bean
    public JdbcTemplate ClickHouseJdbcTemplate() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        Connection conn = DriverManager.getConnection(url, props);
        return new JdbcTemplate(new SingleConnectionDataSource(conn, true));
    }
}
