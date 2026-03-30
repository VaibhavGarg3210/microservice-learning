package com.learn.accounts.service;

import org.springframework.stereotype.Service;

import com.learn.accounts.dto.CustomerDetailsDto;

@Service
public interface ICustomerService {
	
	/**
	 * 
	 * @param mobileNumber
	 * @return
	 */
	CustomerDetailsDto fetchCustomerDetails(String mobileNumber);

}
