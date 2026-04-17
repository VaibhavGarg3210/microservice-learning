package com.learn.accounts.service.impl;

import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.learn.accounts.constants.AccountsConstants;
import com.learn.accounts.dto.AccountsDto;
import com.learn.accounts.dto.AccountsMsgDto;
import com.learn.accounts.dto.CustomerDto;
import com.learn.accounts.entity.Accounts;
import com.learn.accounts.entity.Customer;
import com.learn.accounts.exception.CustomerAlreadyExistsException;
import com.learn.accounts.exception.ResourceNotFoundException;
import com.learn.accounts.mapper.AccountsMapper;
import com.learn.accounts.mapper.CustomerMapper;
import com.learn.accounts.repository.AccountsRepository;
import com.learn.accounts.repository.CustomerRepository;
import com.learn.accounts.service.IAccountsService;

import lombok.AllArgsConstructor;

@Service
//lombook generate all args 
@AllArgsConstructor
public class AccountsServiceImpl  implements IAccountsService {

	private static final Logger log = LoggerFactory.getLogger(AccountsServiceImpl.class);
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private StreamBridge bridge;
    

    /**
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    +customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        Accounts savedAccounts = accountsRepository.save(createNewAccount(savedCustomer));
        sendCommunication(savedAccounts, savedCustomer);
    }
    
    private void sendCommunication(Accounts accounts,Customer customer	) {
    	var accountsMsgDto = new AccountsMsgDto(accounts.getAccountNumber(), customer.getName(), customer.getEmail(), customer.getMobileNumber());
    	log.info("Sending Comminacation request for the details {} ",accountsMsgDto);
    	var result = bridge.send("sendCommunication-out-0", accountsMsgDto);
    	log.info("Is the Communication request successfully processed ? : {} ",result);
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    @Override
    public CustomerDto fetchAccount(@RequestParam String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        //deleteById by default method
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

	@Override
	public boolean updateCommunicationStatus(Long accountNumber) {
		boolean isUpdated = false;
		if(accountNumber!=null) {
			Accounts accounts = accountsRepository.findById(accountNumber).orElseThrow(
					()->new ResourceNotFoundException("Accounts", "AccountNumber", accountNumber.toString())
				);
			accounts.setCommunicationSw(true);
			accountsRepository.save(accounts);
			isUpdated = true;
			}
		return isUpdated;
	}


}
