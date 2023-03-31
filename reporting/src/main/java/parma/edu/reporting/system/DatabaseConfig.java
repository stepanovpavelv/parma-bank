package parma.edu.reporting.system;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DatabaseConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties postgresSqlDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public NamedParameterJdbcTemplate postgresqlJdbcTemplate(@Qualifier("postgresSqlDataSourceProperties")
                                                                 DataSourceProperties properties) {
        HikariDataSource hikariPool = properties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();

        return new NamedParameterJdbcTemplate(hikariPool);
    }
}