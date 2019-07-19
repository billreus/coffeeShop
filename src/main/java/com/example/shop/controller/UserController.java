package com.example.shop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("hello")
    public String hello() {
        return "Hello Spring Security";
    }
/*
    @GetMapping("user/login")
    public Object index(Authentication authentication){
        return authentication;
    }*/
}
