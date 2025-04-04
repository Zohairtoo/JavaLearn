package com.xohairtoo.accounts.controller;

import com.xohairtoo.accounts.constants.AccountsConstants;
import com.xohairtoo.accounts.dto.AccountContactInfoDto;
import com.xohairtoo.accounts.dto.CustomerDto;
import com.xohairtoo.accounts.dto.ErrorResponseDto;
import com.xohairtoo.accounts.dto.ResponseDto;
import com.xohairtoo.accounts.service.IAccountsService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Accounts APi's",
        description = "Accounts APIs which consist of account creation, account fetch, account update, account delete")
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountsController {

    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);

    private final IAccountsService iAccountsService;

    public AccountsController(IAccountsService iAccountsService) {
        this.iAccountsService = iAccountsService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountContactInfoDto accountContactInfoDto;

    @GetMapping("/sayHelloAccounts")
    public String syaHello() {
        return "Hi from Zohair from accounts microservice side";
    }

    @Operation(summary = "Create a new account")
    @ApiResponse(responseCode = "201", description = "Account created successfully")
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        iAccountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(summary = "Fetch account details")
    @ApiResponse(responseCode = "200", description = "Request processed successfully")
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccontDetails(@RequestParam
                                                          @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                          String mobileNumber) {
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @Operation(summary = "Update account details")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error")
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(summary = "Delete account")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam
                                                     @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                     String mobileNumber) {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(summary = "Get Build information",
            description = "Get build information for the Microservice")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @Retry(name = "getBuildInfo", fallbackMethod = "getBuildInfoFallback")
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity.
                status(HttpStatus.OK).
                body(buildVersion);
    }

    public ResponseEntity<String> getBuildInfoFallback(Throwable ex) {
        logger.debug("getBuildInfoFallback() method invoked");
        return ResponseEntity.
                status(HttpStatus.OK).
                body("dummy buildVersion");
    }

    @Operation(summary = "Get Java version",
            description = "Get java version for the Microservice")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @RateLimiter(name = "getJavaVersion")
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity.
                status(HttpStatus.OK).
                body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(summary = "Get Maven version",
            description = "Get Maven version for the Microservice")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/maven-version")
    public ResponseEntity<String> getMavenVersion() {
        return ResponseEntity.
                status(HttpStatus.OK).
                body(environment.getProperty("MAVEN_HOME"));
    }

    @Operation(summary = "Get Contact Information",
            description = "Get contact information for the Microservice")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/contact-info")
    public ResponseEntity<AccountContactInfoDto> getContactInfo() {
        return ResponseEntity.
                status(HttpStatus.OK).
                body(accountContactInfoDto);
    }
}
