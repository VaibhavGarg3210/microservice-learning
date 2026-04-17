package com.learn.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter{

	public static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);
	
	@Autowired
	FilterUtility filterUtility;
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		HttpHeaders headers = exchange.getRequest().getHeaders();
		if(isCorrelationIdPresent(headers)) {
			
		}else {
			String correlationID = generateCorrelationId();
			exchange = filterUtility.setCorrelationID(exchange,correlationID);
			logger.debug("learning-correlation-id generated in RequestTraceFilter : {} ",correlationID);
		}
		return chain.filter(exchange);
	}

	private String generateCorrelationId() {
		return java.util.UUID.randomUUID().toString();
	}

	private boolean isCorrelationIdPresent(HttpHeaders headers) {
		if(filterUtility.getCorrelationId(headers)!=null)
			return true;
		else
			return false;
	}

}
