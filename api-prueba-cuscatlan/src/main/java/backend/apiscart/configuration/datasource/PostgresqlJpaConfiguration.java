package backend.apiscart.configuration.datasource;

import java.util.HashMap;
import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"backend.apiscart" }, 
entityManagerFactoryRef = "postgressqlEntityManagerFactory", 
transactionManagerRef = "postgressqlTransactionManager")
public class PostgresqlJpaConfiguration {

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean postgressqlEntityManagerFactory(
			@Qualifier("postgressqlDataSource") DataSource dataSource, EntityManagerFactoryBuilder builder) {
		HashMap<String, Object> props = new HashMap<>();
		return builder.dataSource(dataSource).properties(props).packages("backend.apiscart").build();
	}

	@Bean
	@Primary
	public PlatformTransactionManager postgressqlTransactionManager(
			@Qualifier("postgressqlEntityManagerFactory") LocalContainerEntityManagerFactoryBean postgressqlEntityManagerFactory) {
		return new JpaTransactionManager(Objects.requireNonNull(postgressqlEntityManagerFactory.getObject()));
	}

}
