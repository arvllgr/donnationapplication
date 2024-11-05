package com.example.restservice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; 
    }
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; 
    }
}
