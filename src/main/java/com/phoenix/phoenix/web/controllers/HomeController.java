package com.phoenix.phoenix.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller

public class HomeController {
    @GetMapping("/home")
    public String showHomePage(){
        return "home";
    }

}
