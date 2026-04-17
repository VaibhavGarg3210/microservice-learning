package com.learn.accounts.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.learn.accounts.dto.LoansDto;

@FeignClient(name = "loans",fallback = LoansFallback.class)
public interface LoansFeignClient {

	@GetMapping("/api/fetch")
    ResponseEntity<LoansDto> fetchLoanDetails(@RequestHeader("learning-correlation-id") String correlationId ,
        @RequestParam("mobileNumber") String mobileNumber
    );
}
