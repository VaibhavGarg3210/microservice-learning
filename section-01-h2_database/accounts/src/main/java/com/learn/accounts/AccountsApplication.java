package com.learn.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

/**
 * @author vaibhav
 */
@SpringBootApplication
// Add Details in heading of front page in swagger page
@OpenAPIDefinition(info = @Info(title = "Account Microservice REST Api Documentation",
description = "Learning Microservice", version = "v1", contact = @Contact(name = "Vaibhav Garg", 
email = "Vaibhavgarg3210@gmail.com", url = "https://github.com/vaibhavgarg3210"), license = @License(name = "Apache 2.0", url = "https://github.com/vaibhavgarg3210")), externalDocs = @ExternalDocumentation(description = "Learning Microservice", url = "ttps://github.com/vaibhavgarg3210"))
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
