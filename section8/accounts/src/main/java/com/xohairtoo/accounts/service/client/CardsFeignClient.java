package com.xohairtoo.accounts.service.client;

import com.xohairtoo.accounts.dto.CardsDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface CardsFeignClient {
    @GetMapping("/api/fetch")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam String mobileNumber);
}
