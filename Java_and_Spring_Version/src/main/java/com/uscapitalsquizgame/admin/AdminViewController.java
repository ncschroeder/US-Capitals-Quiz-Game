package com.uscapitalsquizgame.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminViewController {
    @GetMapping("/admin")
    public String admin() {
        return "admin.html";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
//        return "login.html";
    }
}