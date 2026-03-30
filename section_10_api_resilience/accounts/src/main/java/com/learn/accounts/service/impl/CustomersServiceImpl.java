package com.learn.accounts.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.learn.accounts.dto.AccountsDto;
import com.learn.accounts.dto.CardsDto;
import com.learn.accounts.dto.CustomerDetailsDto;
import com.learn.accounts.dto.LoansDto;
import com.learn.accounts.entity.Accounts;
import com.learn.accounts.entity.Customer;
import com.learn.accounts.exception.ResourceNotFoundException;
import com.learn.accounts.mapper.AccountsMapper;
import com.learn.accounts.mapper.CustomerMapper;
import com.learn.accounts.repository.AccountsRepository;
import com.learn.accounts.repository.CustomerRepository;
import com.learn.accounts.service.ICustomerService;
import com.learn.accounts.service.client.CardsFeignClient;
import com.learn.accounts.service.client.LoansFeignClient;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomerService {
	private AccountsRepository accountsRepository;
	private CustomerRepository customerRepository;
	private LoansFeignClient loansFeignClient;
	private CardsFeignClient cardsFeignClient;

	@Override
//	public CustomerDetailsDto fetchCustomerDetails(String correlationId,String mobileNumber) {
	public CustomerDetailsDto fetchCustomerDetails(String mobileNumber,String correlationId) {
		 Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
	                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
	        );
	        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
	                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
	        );
	        System.err.println("Working ...  ");
	        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
	        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
//	        ResponseEntity<LoansDto> loanResponse = loansFeignClient.fetchLoanDetails(correlationId,mobileNumber);
	        ResponseEntity<LoansDto> loanResponse = loansFeignClient.fetchLoanDetails(correlationId,mobileNumber);
	        if(loanResponse!=null)
	        customerDetailsDto.setLoansDto(loanResponse.getBody());
//	        ResponseEntity<CardsDto> cardResponse = cardsFeignClient.fetchCardDetails(correlationId,mobileNumber);
	        ResponseEntity<CardsDto> cardResponse = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
	        if(cardResponse!=null)
	        customerDetailsDto.setCardsDto(cardResponse.getBody());
	        return customerDetailsDto;
	}

}
