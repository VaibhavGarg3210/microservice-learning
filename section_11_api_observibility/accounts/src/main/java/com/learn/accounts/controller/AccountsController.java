package com.learn.accounts.controller;


import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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
import com.learn.accounts.dto.AccountsContactInfoDto;
import com.learn.accounts.dto.CustomerDto;
import com.learn.accounts.dto.ErrorResponseDto;
import com.learn.accounts.dto.ResponseDto;
import com.learn.accounts.service.IAccountsService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
// this tag is use for controller purpose
@Tag(
		name = "CRUD REST APIs for Account Service",
		description = "CRUD REST APIs in Learning tutorial CREATE,FETCH,UPDATE,DELETE"
		)
//@AllArgsConstructor
@Validated
public class AccountsController {

	public static Logger logger = LoggerFactory.getLogger(AccountsController.class);
	private final IAccountsService iAccountsService;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	AccountsContactInfoDto accountsContactInfoDto;
	
	public AccountsController(IAccountsService IAccountsService) {
		this.iAccountsService = IAccountsService;
	}
	
	@Value("${build.version}")
	private String buildVersion;
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
	
	@Operation(
			summary = "GET BUILD INFO",
			description = "REST API to Fetch Build Version"
			)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP STATUS SUCCESS"
			)
	@Retry(name = "getBuildInfo",fallbackMethod = "getBuildInfoFallback")
	@GetMapping("/buildVersion")
	public ResponseEntity<String> getBuildInfo() throws TimeoutException {
		logger.info("Start of getBuildInfo");
//		throw new TimeoutException();
		return ResponseEntity.status(HttpStatus.OK).body(buildVersion) ;
	}
	
	@GetMapping("/getBuildInfoFallback")
	public ResponseEntity<String> getBuildInfoFallback(Throwable throwable) {
		logger.info ("Start of getBuildInfoFallback");
		return ResponseEntity.status(HttpStatus.OK).body("0.9") ;
	}
	
	@Operation(
			summary = "GET JAVA Version",
			description = "REST API to Fetch JAVA Version"
			)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP STATUS SUCCESS"
			)
	@RateLimiter(name = "getJavaVersion",fallbackMethod = "getJavaVersionFallbackMethod")
	@GetMapping("/java-version")
	public ResponseEntity<String> getJavaVersion() {
		return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("java.home")) ;
	}
	
	public ResponseEntity<String> getJavaVersionFallbackMethod(Throwable throwable){
		return ResponseEntity.status(HttpStatus.OK).body("Java 17");
	}
	
	@Operation(
			summary = "GET Contact Info",
			description = "Contact info details that can be reached out in case of issues"
			)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP STATUS SUCCESS"
			)
	@GetMapping("/get-contact-info")
	public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
		return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDto) ;
	}
	
}
