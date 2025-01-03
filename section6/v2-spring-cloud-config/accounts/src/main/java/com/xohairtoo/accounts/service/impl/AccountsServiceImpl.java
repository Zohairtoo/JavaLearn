package com.xohairtoo.accounts.service.impl;

import com.xohairtoo.accounts.constants.AccountsConstants;
import com.xohairtoo.accounts.dto.AccountsDto;
import com.xohairtoo.accounts.dto.CustomerDto;
import com.xohairtoo.accounts.entity.Accounts;
import com.xohairtoo.accounts.entity.Customer;
import com.xohairtoo.accounts.exception.CustomerAlreadyExistsException;
import com.xohairtoo.accounts.exception.ResourceNotFoundException;
import com.xohairtoo.accounts.mapper.AccountsMapper;
import com.xohairtoo.accounts.mapper.CustomerMapper;
import com.xohairtoo.accounts.repository.AccountsRepository;
import com.xohairtoo.accounts.repository.CustomerRepository;
import com.xohairtoo.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     * @param customerDto - CustomerDto object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
          throw new CustomerAlreadyExistsException("Customer Already present with given mobile number "
                  + customerDto.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Zohair");
        Customer savedCustomer =  customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));

    }

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDto customerDto =  CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    /**
     *
     * @param customer - Customer object
     * @return new account details
     */
    private Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedBy("Zohair");
        newAccount.setCreatedAt(LocalDateTime.now());
        return newAccount;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto){
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto !=null){
        Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Accout", "AccountNumber", accountsDto.getAccountNumber().toString())
        );
        AccountsMapper.mapToAccount(accountsDto, accounts);
        accounts = accountsRepository.save(accounts);

        Long customerId = accounts.getCustomerId();
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
        );
        CustomerMapper.mapToCustomer(customerDto, customer);
        customerRepository.save(customer);
        isUpdated = true;
    }
    return isUpdated;
}

@Override
    public boolean deleteAccount(String mobileNumber){
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
}

}
