package com.example.userservice.controller;

import com.example.userservice.RpsCounter;
import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RpsCounter rpsCounter;   // <-- добавлено

    public UserController(UserService userService, RpsCounter rpsCounter) { // <-- обновлён конструктор
        this.userService = userService;
        this.rpsCounter = rpsCounter;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        rpsCounter.increment();             // <-- добавлено
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        rpsCounter.increment();             // <-- добавлено
        return userService.getUser(id);
    }
}