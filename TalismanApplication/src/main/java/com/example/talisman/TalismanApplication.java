package com.example.talisman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude={MultipartAutoConfiguration.class, RepositoryRestMvcConfiguration.class})
@EnableJpaRepositories(basePackages = "com.example.talisman.repositories")
@EnableTransactionManagement
@EnableSpringDataWebSupport
@Import(RepositoryRestMvcConfiguration.class)
public class TalismanApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalismanApplication.class, args);
	}
}
