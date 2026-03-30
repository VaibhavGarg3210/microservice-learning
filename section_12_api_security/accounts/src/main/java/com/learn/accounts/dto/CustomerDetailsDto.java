package com.learn.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Schema to hold Customer, Account, Cards and Loans information")
public class CustomerDetailsDto {
	
	    private String name;

	    private String email;

	    private String mobileNumber;

	    private AccountsDto accountsDto;
	    
	    private LoansDto loansDto;
	    
	    private CardsDto cardsDto;
	    

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getMobileNumber() {
			return mobileNumber;
		}

		public void setMobileNumber(String mobileNumber) {
			this.mobileNumber = mobileNumber;
		}

		public AccountsDto getAccountsDto() {
			return accountsDto;
		}

		public void setAccountsDto(AccountsDto accountsDto) {
			this.accountsDto = accountsDto;
		}

	    
	    


}
