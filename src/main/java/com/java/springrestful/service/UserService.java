package com.java.springrestful.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.springrestful.domain.User;
import com.java.springrestful.domain.dto.CreateUserResultDTO;
import com.java.springrestful.domain.dto.MetaData;
import com.java.springrestful.domain.dto.PagingResultDTO;
import com.java.springrestful.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public PagingResultDTO handleGetAllUser(Specification<User> specification, Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(specification, pageable);

        PagingResultDTO pagingResultDTO = new PagingResultDTO();
        MetaData metaData = new MetaData();

        // getNumber() start = 0
        metaData.setPage(pageable.getPageNumber() + 1);
        metaData.setPageSize(pageable.getPageSize());

        metaData.setPages(pageUser.getTotalPages());
        metaData.setTotal(pageUser.getTotalElements());

        pagingResultDTO.setMetaData(metaData);
        pagingResultDTO.setResult(pageUser.getContent());

        return pagingResultDTO;
    }

    public User handleGetUserById(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        return null;
    }

    public User handleCreateUser(User user) {
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        return this.userRepository.save(user);
    }

    public User handleUpdateUserById(Long id, User user) {
        User userUpdate = this.handleGetUserById(id);
        userUpdate.setEmail(user.getEmail());
        userUpdate.setName(user.getName());
        userUpdate.setPassword(user.getPassword());
        return this.userRepository.save(userUpdate);
    }

    public void handleDeleteUserById(Long id) {
        this.userRepository.deleteById(id);
    }

    public User handleGetUserByUsername(String email) {
        return this.userRepository.findByEmail(email);
    }

    public CreateUserResultDTO handleCreateUserResultDTO(User user) {
        CreateUserResultDTO createUserResultDTO = new CreateUserResultDTO();
        createUserResultDTO.setId(user.getId());
        createUserResultDTO.setName(user.getName());
        createUserResultDTO.setAge(user.getAge());
        createUserResultDTO.setEmail(user.getEmail());
        createUserResultDTO.setGender(user.getGender());
        createUserResultDTO.setAddress(user.getAddress());
        createUserResultDTO.setCreateAt(user.getCreateAt());
        createUserResultDTO.setCreateBy(user.getCreateBy());
        return createUserResultDTO;
    }

    public boolean existsUserByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }
}
