package com.example.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @CrossOrigin(origins = "https://550295mjyq84.vicp.fun")
    @GetMapping("/api/endpoint")
    public String getData() {
        return "Hello from Spring Boot!";
    }
}