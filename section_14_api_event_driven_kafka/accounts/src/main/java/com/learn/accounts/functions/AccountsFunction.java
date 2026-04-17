package com.learn.accounts.functions;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learn.accounts.service.IAccountsService;

@Configuration
public class AccountsFunction {

	private static final Logger log = LoggerFactory.getLogger(AccountsFunction.class);
	
	@Bean
	public Consumer<Long> updateComminication(IAccountsService accountsService){
		return accNumber ->{
			log.info("Updating Communication status for the accounts number : "+accNumber.toString());
			accountsService.updateCommunicationStatus(accNumber);
		};
	}
	
}
