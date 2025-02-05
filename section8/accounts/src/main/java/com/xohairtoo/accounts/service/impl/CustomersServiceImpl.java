package com.xohairtoo.accounts.service.impl;

import com.xohairtoo.accounts.dto.*;
import com.xohairtoo.accounts.entity.Accounts;
import com.xohairtoo.accounts.entity.Customer;
import com.xohairtoo.accounts.exception.ResourceNotFoundException;
import com.xohairtoo.accounts.mapper.AccountsMapper;
import com.xohairtoo.accounts.mapper.CustomerMapper;
import com.xohairtoo.accounts.repository.AccountsRepository;
import com.xohairtoo.accounts.repository.CustomerRepository;
import com.xohairtoo.accounts.service.ICustomersService;
import com.xohairtoo.accounts.service.client.CardsFeignClient;
import com.xohairtoo.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetailsDto(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        // Now in order to get loans and cards we need use feign clients b/c they are separate microservice
        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity =  cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
