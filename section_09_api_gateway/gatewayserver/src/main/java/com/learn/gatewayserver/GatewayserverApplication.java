package com.learn.gatewayserver;

import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Local;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator learningBankConfig(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/learning/accounts/**")
						.filters(f -> f.rewritePath("/learning/accounts/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDate.now().toString()))
						.uri("lb://ACCOUNTS"))
				.route(p -> p.path("/learning/loans/**")
						.filters(f -> f.rewritePath("/learning/loans/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDate.now().toString()))
						.uri("lb://LOANS"))
				.route(p -> p.path("/learning/cards/**")
						.filters(f -> f.rewritePath("/learning/cards/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDate.now().toString()))
						.uri("lb://CARDS"))
				.build();
	}

}
