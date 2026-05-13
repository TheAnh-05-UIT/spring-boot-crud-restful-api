package com.java.springrestful.controller;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.springrestful.domain.User;
import com.java.springrestful.domain.dto.PagingResultDTO;
import com.java.springrestful.service.UserService;
import com.turkraft.springfilter.boot.Filter;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<PagingResultDTO> getAllUser(
            @Filter Specification<User> specification, Pageable pageable) {

        PagingResultDTO listUserPaging = this.userService.handleGetAllUser(specification, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listUserPaging);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User userById = this.userService.handleGetUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userById);
    }

    @PostMapping("users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = this.userService.handleCreateUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("users/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable("id") Long id,
            @RequestBody User user) {
        User userUpdate = this.userService.handleUpdateUserById(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdate);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
        this.userService.handleDeleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Delete Success");
    }
}
