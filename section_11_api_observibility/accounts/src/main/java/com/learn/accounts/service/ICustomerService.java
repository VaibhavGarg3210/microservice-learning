package com.learn.accounts.service;

import org.springframework.stereotype.Service;

import com.learn.accounts.dto.CustomerDetailsDto;

@Service
public interface ICustomerService {
	
	/**
	 * 
	 * @param mobileNumber
	 * @param correlationId 
	 * @return
	 */
	CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);

}
