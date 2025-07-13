package com.learn.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.accounts.constants.AccountsConstants;
import com.learn.accounts.dto.CustomerDto;
import com.learn.accounts.dto.ErrorResponseDto;
import com.learn.accounts.dto.ResponseDto;
import com.learn.accounts.service.IAccountsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
// this tag is use for controller purpose
@Tag(
		name = "CRUD REST APIs for Account Service",
		description = "CRUD REST APIs in Learning tutorial CREATE,FETCH,UPDATE,DELETE"
		)
@AllArgsConstructor
@Validated
public class AccountsController {

	private IAccountsService iAccountsService;
	//tell client what this is doing
	@Operation(
			summary = "Create Account REST API",
			description = "REST API to create new Customer & Account inside Learning Bank Service	"
			)
	//change default status 200
	@ApiResponse(
			responseCode = "201",
			description = "HTTP STATUS CREATED"
			)
	@PostMapping("/create")
	public ResponseEntity<?> createAccount(@Valid @RequestBody CustomerDto customerDto) {
		iAccountsService.createAccount(customerDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
	}
	
	@Operation(
			summary = "Fetch Account REST API",
			description = "REST API to Fetch new Customer & Account inside Learning Bank Service	"
			)
	//change default status 200
	@ApiResponse(
			responseCode = "200",
			description = "HTTP STATUS SUCCESS"
			)
	@GetMapping("/fetch")
	public ResponseEntity<CustomerDto> fetchAccountDetails( @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")  String mobileNumber) {
		CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);
	}
	
	@Operation(
			summary = "Update Account REST API",
			description = "REST API to update new Customer & Account inside Learning Bank Service	"
			)
	//change default status 200
	@ApiResponse(
			responseCode = "200",
			description = "HTTP UPDATE SUCCESSFULLY",
			content = @Content(
					schema = @Schema(implementation = ErrorResponseDto.class))
			)
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody  CustomerDto customerDto) {
		boolean isUpdated = iAccountsService.updateAccount(customerDto);
		if (isUpdated) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
		}
	}
	
	@Operation(
			summary = "Create Account REST API",
			description = "REST API to delete new Customer & Account inside Learning Bank Service	"
			)
	//change default status 200
	@ApiResponse(
			responseCode = "200",
			description = "HTTP STATUS DELETE"
			)
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteAccountDetails(
			@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") @RequestParam String mobileNumber) {
		boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
		}
	}

}
