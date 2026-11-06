package com.example.sesac_spring_boot_study.controller;

import com.example.sesac_spring_boot_study.service.GreetingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetController {
    private final GreetingService service;

    public GreetController(GreetingService service) {
        this.service = service;
    }

    @GetMapping("/greet")
    public String greet(@RequestParam(value = "name", defaultValue = "방문자") String name) {
        return service.greet(name);
    }

}
