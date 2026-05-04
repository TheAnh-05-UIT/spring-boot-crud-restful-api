package com.java.springrestful.service;

import java.util.List;
import java.util.Optional;

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

    public User handleGetUserById(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public void handleDeleteUserById(Long id) {
        this.userRepository.deleteById(id);
    }
}
