package dev.ynnk.m295.controller;

import dev.ynnk.m295.model.User;
import dev.ynnk.m295.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }



}
