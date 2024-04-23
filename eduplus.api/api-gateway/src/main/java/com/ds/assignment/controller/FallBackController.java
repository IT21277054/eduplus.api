package com.ds.assignment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {
    @RequestMapping("/notificationFallBack")
    public Mono<String> orderServiceFallBack() {
        return Mono.just("notification Service is taking too long to respond or is down. Please try again later");
    }
}
