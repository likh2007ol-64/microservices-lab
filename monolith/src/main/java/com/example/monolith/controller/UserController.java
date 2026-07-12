package com.example.monolith.controller;

import com.example.monolith.RpsCounter;
import com.example.monolith.entity.User;
import com.example.monolith.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RpsCounter rpsCounter;

    public UserController(UserService userService, RpsCounter rpsCounter) {
        this.userService = userService;
        this.rpsCounter = rpsCounter;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        rpsCounter.increment();
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        rpsCounter.increment();
        return userService.getUser(id);
    }
}