package com.alibou.security;

import com.alibou.security.auth.AuthenticationService;
import com.alibou.security.auth.RegisterRequest;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.support.DatabaseStartupValidator;

import javax.sql.DataSource;

import java.util.stream.Stream;

import static com.alibou.security.enums.Role.ADMIN;
import static com.alibou.security.enums.Role.MANAGER;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Bean
	public DatabaseStartupValidator databaseStartupValidator(DataSource dataSource) {
		var dsv = new DatabaseStartupValidator();
		dsv.setDataSource(dataSource);
		dsv.setValidationQuery(DatabaseDriver.MYSQL.getValidationQuery());
		return dsv;
	}

	@Bean
	public static BeanFactoryPostProcessor dependsOnPostProcessor() {
		return bf -> {
			// Let beans that need the database depend on the DatabaseStartupValidator
			// like the JPA EntityManagerFactory or Flyway

			String[] jpa = bf.getBeanNamesForType(EntityManagerFactory.class);
			Stream.of(jpa)
					.map(bf::getBeanDefinition)
					.forEach(it -> it.setDependsOn("databaseStartupValidator"));
		};
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			try {
				var admin = RegisterRequest.builder()
						.firstname("Admin")
						.lastname("Admin")
						.email("admin@mail.com")
						.password("password")
						.role(ADMIN)
						.build();
				System.out.println("Admin token: " + service.register(admin).getAccessToken());

				var manager = RegisterRequest.builder()
						.firstname("Admin")
						.lastname("Admin")
						.email("manager@mail.com")
						.password("password")
						.role(MANAGER)
						.build();
				System.out.println("Manager token: " + service.register(manager).getAccessToken());
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}
}
