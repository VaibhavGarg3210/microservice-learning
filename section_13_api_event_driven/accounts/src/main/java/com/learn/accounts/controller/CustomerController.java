package com.learn.accounts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.accounts.dto.CustomerDetailsDto;
import com.learn.accounts.service.ICustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;

@RestController
// this tag is use for controller purpose
@Tag(
		name = "CRUD REST APIs for Customers Service",
		description = "CRUD REST APIs to get Customer Details form loans, cards, accounts"
		)
//@AllArgsConstructor
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
public class CustomerController {
	
	public static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	
	private final ICustomerService customerService;
	
	public CustomerController(ICustomerService customerService) {
		this.customerService = customerService;
	}

	@Operation(
			summary = "Fetch CustomerDetails REST API",
			description = "REST API to Fetch new Customer & Account, Loan, Card inside Learning Bank Service	"
			)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP STATUS SUCCESS"
			)
	@GetMapping(value = "/fetchCustomerDetails")
	public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader("learning-correlation-id") String correlationId, @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")  String mobileNumber) {
		logger.debug("Start fetchCustomerDetails ");
		logger.debug("learning correlation found : {} ",correlationId);
		CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(mobileNumber,correlationId);
		logger.debug("End fetchCustomerDetails ");
		return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
	}
	
}
