package com.xohairtoo.accounts.service;


import com.xohairtoo.accounts.dto.CustomerDetailsDto;

public interface ICustomersService {

    /**
     *
     * @param mobileNumber
     * @return
     */
    CustomerDetailsDto fetchCustomerDetailsDto(String mobileNumber, String correlationId);
}
