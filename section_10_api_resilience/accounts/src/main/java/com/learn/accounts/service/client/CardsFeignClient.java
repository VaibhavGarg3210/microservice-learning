package com.learn.accounts.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.learn.accounts.dto.CardsDto;

@FeignClient(name="cards",fallback = CardsFallback.class)
public interface CardsFeignClient {

	@GetMapping(value = "/api/fetch")
	public ResponseEntity<CardsDto> fetchCardDetails(@RequestHeader("learning-correlation-id") String correlationId , @RequestParam("mobileNumber") String mobileNumber);
}
