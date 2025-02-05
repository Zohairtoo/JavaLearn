package com.xohairtoo.accounts;

import com.xohairtoo.accounts.dto.AccountContactInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.ExternalDocumentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
/*@ComponentScans({ @ComponentScan("com.xohairtoo.accounts.controller") })
@EnableJpaRepositories("com.xohairtoo.accounts.repository")
@EntityScan("com.xohairtoo.accounts.model")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {AccountContactInfoDto.class})
@OpenAPIDefinition(info = @Info(
		title = "Accounts microservice REST API Documentation",
		description = "Accounts microservice REST API Documentation",
		version = "v1",
		contact = @Contact(
				name = "Zohair Ahmed",
				email = "zohairtoo@gmail.com",
				url = "https://github.com/Zohairtoo/JavaLearn/tree/master/section2"
		),
		license = @License(
				name = "Apache 2.0",
				url = "https://github.com/Zohairtoo/JavaLearn/tree/master/section2"
		)
),
		externalDocs = @ExternalDocumentation(
				description =  "Accounts microservice REST API Documentation",
				url = "https://www.google.com/")
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
