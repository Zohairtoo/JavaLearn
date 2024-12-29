package com.xohairtoo.loans;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/*@ComponentScans({ @ComponentScan("com.xohairtoo.loans.controller") })
@EnableJpaRepositories("com.xohairtoo.loans.repository")
@EntityScan("com.xohairtoo.cards.model")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Loans microservice REST API Documentation",
				description = "Loans microservice REST API Documentation",
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
				description = "Loans microservice REST API Documentation",
				url = "https://www.google.com/"
		)
)
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}
