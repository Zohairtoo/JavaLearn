package com.xohairtoo.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer Details",
        description = "Schema to hold Customer, loans, cas and account details"
)
public class CustomerDetailsDto {
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 5, max = 50, message = "Name must be between 5 and 50 characters")
    @Schema(
            name = "Customer",
            description = "Name  of  the Customer"
    )
    private String name;


    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Schema(
            name = "Email",
            description = "Email of the Customer"
    )
    private String email;

    @Schema(
            description = "Mobile Number of the customer", example = "9345432123"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Account details of the Customer"
    )
    private AccountsDto accountsDto;

    @Schema(
            description = "Loans details of the Customer"
    )
    private LoansDto loansDto;

    @Schema(
            description = "Cards details of the Customer"
    )
    private CardsDto cardsDto;
}
