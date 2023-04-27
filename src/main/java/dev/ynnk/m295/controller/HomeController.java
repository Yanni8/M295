package dev.ynnk.m295.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/swagger-ui.html";
    }
}
