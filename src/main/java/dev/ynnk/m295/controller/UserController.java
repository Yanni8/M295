package dev.ynnk.m295.controller;

import dev.ynnk.m295.model.User;
import dev.ynnk.m295.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }


    @GetMapping("/api/v1/user")
    public List<User> getAllUsers(){
        return this.service.getAllUser();
    }


    @GetMapping("/api/v1/user/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return this.service.getUserById(id);
    }

    @PostMapping("/api/v1/user")
    public User createUser(@RequestBody @Valid User user){
        return this.service.saveUser(user);
    }

    @PatchMapping("/api/v1/user/{id}")
    public User patchUser(@RequestBody User partialUser, @PathVariable("id") Long id){
        return this.service.patchUser(partialUser,id);
    }
    
    @PutMapping("/api/v1/user/{id}")
    public User updateUser(@RequestBody @Valid User user, @PathVariable("id") Long id){
        return this.service.updateUser(user, id);
    }
}
