package com.java.springrestful.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.springrestful.domain.User;
import com.java.springrestful.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> listUser = this.userService.handleGetAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(listUser);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User userById = this.userService.handleGetUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userById);
    }

    @PostMapping("users/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = this.userService.handleCreateUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
