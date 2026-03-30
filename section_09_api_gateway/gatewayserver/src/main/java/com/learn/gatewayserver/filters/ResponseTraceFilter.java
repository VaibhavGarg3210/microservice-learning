package com.learn.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import reactor.core.publisher.Mono;

@Configuration
public class ResponseTraceFilter {

	public static final Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);
	
	@Autowired
	FilterUtility filterUtility;
	
	@Bean
	public GlobalFilter postGlobalFilter() {
		return(exchance,chain)->{
			return chain.filter(exchance).then(Mono.fromRunnable(()->{
				HttpHeaders headers = 	exchance.getRequest().getHeaders();
				String correlationId = filterUtility.getCorrelationId(headers);
				logger.debug("Updated the correlation id to the outbound headers : {} ",correlationId);
				exchance.getResponse().getHeaders().add(filterUtility.CORRELATION_ID, correlationId);
			}));
		};
	}
}
