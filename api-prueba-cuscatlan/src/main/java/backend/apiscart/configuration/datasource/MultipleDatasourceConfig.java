package backend.apiscart.configuration.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MultipleDatasourceConfig {

	@Bean
	@Primary
	@Qualifier("postgresqlDataSourceProperties")
	@ConfigurationProperties("spring.shopping-cart.datasource")
	public DataSourceProperties postgresqlDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@Qualifier("postgressqlDataSource")
	@ConfigurationProperties("spring.shopping-cart.datasource.hikari")
	public DataSource postgresqlDataSource() {
		return postgresqlDataSourceProperties().initializeDataSourceBuilder().build();
	}

}
