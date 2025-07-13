package com.learn.cards;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.learn.cards.dto.CardsContactInfoDto;

@SpringBootApplication
/*@ComponentScans({ @ComponentScan("com.learn.cards.controller") })
@EnableJpaRepositories("com.learn.cards.repository")
@EntityScan("com.learn.cards.model")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {CardsContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Cards microservice REST API Documentation",
				description = "Mybank Cards microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Vaibhav Garg",
						email = "tutor@learn.com",
						url = "https://www.learn.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.learn.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Mybank Cards microservice REST API Documentation",
				url = "https://www.learn.com/swagger-ui.html"
		)
)
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}
}
