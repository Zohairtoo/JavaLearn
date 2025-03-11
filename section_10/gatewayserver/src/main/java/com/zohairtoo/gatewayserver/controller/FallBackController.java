package com.zohairtoo.gatewayserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

public class FallBackController {

    @RequestMapping("/contactSupport")
    public Mono<String> contactSupport() {
        return Mono.just("An error occurred. Please try after some time or contact support team!!!");
    }
}
