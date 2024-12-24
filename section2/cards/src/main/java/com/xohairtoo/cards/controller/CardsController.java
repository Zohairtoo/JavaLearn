package com.xohairtoo.cards.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CardsController {
    @GetMapping("/sayHelloCards")
    public String syaHello(){
        return "Hi from Zohair from cards microservice side";
    }
}
