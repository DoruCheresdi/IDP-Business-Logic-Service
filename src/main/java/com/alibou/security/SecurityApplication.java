package com.alibou.security;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.support.DatabaseStartupValidator;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;

import java.util.Arrays;
import java.util.stream.Stream;


@SpringBootApplication
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
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("http://localhost:8084");
		corsConfiguration.addAllowedOrigin("http://localhost:8081");
//		corsConfiguration.addAllowedOriginPattern("*");
		corsConfiguration.setAllowedMethods(Arrays.asList(
				HttpMethod.GET.name(),
				HttpMethod.HEAD.name(),
				HttpMethod.POST.name(),
				HttpMethod.PUT.name(),
				HttpMethod.DELETE.name()));
		corsConfiguration.setMaxAge(1800L);
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedHeader("Content-Type");
		corsConfiguration.addAllowedHeader("Authorization");
//		corsConfiguration.addExposedHeader("header1");
		source.registerCorsConfiguration("/**", corsConfiguration); // you restrict your path here
		return source;
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(
//			AuthenticationService service
//	) {
//		return args -> {
//			try {
//				var admin = RegisterRequest.builder()
//						.firstname("Admin")
//						.lastname("Admin")
//						.email("admin@mail.com")
//						.password("password")
//						.role(ADMIN)
//						.build();
//				System.out.println("Admin token: " + service.register(admin).getAccessToken());
//
//				var manager = RegisterRequest.builder()
//						.firstname("Admin")
//						.lastname("Admin")
//						.email("manager@mail.com")
//						.password("password")
//						.role(MANAGER)
//						.build();
//				System.out.println("Manager token: " + service.register(manager).getAccessToken());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		};
//	}
}
