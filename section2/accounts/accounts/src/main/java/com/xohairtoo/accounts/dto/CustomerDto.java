package com.xohairtoo.accounts.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class CustomerDto {

    private String name;

    private String email;

    private String mobileNumber;
}
