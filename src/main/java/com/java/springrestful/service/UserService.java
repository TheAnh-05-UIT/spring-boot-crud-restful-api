package com.java.springrestful.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java.springrestful.domain.User;
import com.java.springrestful.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> handleGetAllUser() {
        return this.userRepository.findAll();
    }
}
