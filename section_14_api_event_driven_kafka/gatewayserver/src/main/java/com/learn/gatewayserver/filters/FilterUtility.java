package com.learn.gatewayserver.filters;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class FilterUtility {

	public final String CORRELATION_ID = "learning-correlation-id";
	
	public ServerWebExchange setCorrelationID(ServerWebExchange exchange, String correlationID) {
		return this.setRequestHeader(exchange, CORRELATION_ID, correlationID);
		
	}

	public String getCorrelationId(HttpHeaders headers) {
		if(headers.get(CORRELATION_ID)!=null) {
			List<String> requestHeaderList = headers.get(CORRELATION_ID);
			return requestHeaderList.stream().findFirst().get();
		}
		return null;
	}

	public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name,String value) {
		return exchange.mutate().request(exchange.getRequest().mutate().header(name,value).build()).build();
	}

}
