package com.java.springrestful.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.springrestful.domain.User;
import com.java.springrestful.domain.dto.CreateUserResultDTO;
import com.java.springrestful.domain.dto.PagingResultDTO;
import com.java.springrestful.domain.dto.ResponseUserDTO;
import com.java.springrestful.service.UserService;
import com.java.springrestful.util.annotation.ApiMessage;
import com.java.springrestful.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @ApiMessage("Get All Users")
    public ResponseEntity<PagingResultDTO> getAllUser(
            @Filter Specification<User> specification, Pageable pageable) {

        PagingResultDTO listUserPaging = this.userService.handleGetAllUser(specification, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listUserPaging);
    }

    @GetMapping("/users/{id}")
    @ApiMessage("Get User by Id")
    public ResponseEntity<ResponseUserDTO> getUserById(@PathVariable("id") Long id) throws IdInvalidException {
        User userById = this.userService.handleGetUserById(id);
        if (userById == null) {
            throw new IdInvalidException("Id " + id + " does not exist");
        }
        ResponseUserDTO convResponseUserDTO = this.userService.convertToResponseUserDTO(userById);
        return ResponseEntity.status(HttpStatus.OK).body(convResponseUserDTO);
    }

    @PostMapping("users")
    @ApiMessage("Create a User")
    public ResponseEntity<CreateUserResultDTO> createUser(@RequestBody User user) throws IdInvalidException {
        boolean isEmailExist = this.userService.existsUserByEmail(user.getEmail());
        if (isEmailExist) {
            throw new IdInvalidException("Email " + user.getEmail() + " already exists, please use another email.");
        }

        User newUser = this.userService.handleCreateUser(user);
        CreateUserResultDTO createUserResultDTO = this.userService.handleCreateUserResultDTO(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResultDTO);
    }

    @PutMapping("users/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable("id") Long id,
            @RequestBody User user) {
        User userUpdate = this.userService.handleUpdateUserById(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdate);
    }

    @DeleteMapping("users/{id}")
    @ApiMessage("Delelte a User")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") Long id)
            throws IdInvalidException {
        User deleteUser = this.userService.handleGetUserById(id);
        if (deleteUser == null) {
            throw new IdInvalidException("Id " + id + " does not exist");
        }
        this.userService.handleDeleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
