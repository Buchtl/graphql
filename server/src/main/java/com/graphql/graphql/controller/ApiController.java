package com.graphql.graphql.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class ApiController {

    @GetMapping ("/hello")
    public String hello() {
        return "Hello World!";
    }
}
