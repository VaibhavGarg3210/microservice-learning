//package com.learn.accounts.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import com.learn.accounts.dto.AccountsDto;
//import com.learn.accounts.dto.CardsDto;
//import com.learn.accounts.dto.CustomerDetailsDto;
//import com.learn.accounts.dto.LoansDto;
//import com.learn.accounts.entity.Accounts;
//import com.learn.accounts.entity.Customer;
//import com.learn.accounts.exception.ResourceNotFoundException;
//import com.learn.accounts.mapper.AccountsMapper;
//import com.learn.accounts.mapper.CustomerMapper;
//import com.learn.accounts.repository.AccountsRepository;
//import com.learn.accounts.repository.CustomerRepository;
//import com.learn.accounts.service.ICustomerService;
//import com.learn.accounts.service.client.CardsFeignClient;
//import com.learn.accounts.service.client.LoanFeignClient;
//@Service
//public class CustomersServiceImpl implements ICustomerService {
//	@Autowired
//	private AccountsRepository accountsRepository;
//	@Autowired
//	private CustomerRepository customerRepository;
//	@Autowired
//	private LoanFeignClient loanFeignClient;
//	@Autowired	
//	private CardsFeignClient cardsFeignClient;
//
//	@Override
//	public CustomerDetailsDto fetchCustomerDetails(String correlationId,String mobileNumber) {
//		 Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
//	                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
//	        );
//	        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
//	                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
//	        );
//	        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
//	        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
//	        ResponseEntity<LoansDto> loanResponse = loanFeignClient.fetchLoanDetails(correlationId,mobileNumber);
//	        if(loanResponse!=null)
//	        customerDetailsDto.setLoansDto(loanResponse.getBody());
//	        ResponseEntity<CardsDto> cardResponse = cardsFeignClient.fetchCardDetails(correlationId,mobileNumber);
//	        if(cardResponse!=null)
//	        customerDetailsDto.setCardsDto(cardResponse.getBody());
//	        return customerDetailsDto;
//	}
//
//}
