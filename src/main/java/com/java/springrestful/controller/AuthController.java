package com.java.springrestful.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.springrestful.domain.User;
import com.java.springrestful.domain.dto.LoginDTO;
import com.java.springrestful.domain.dto.ResponseLoginDTO;
import com.java.springrestful.service.SecurityService;
import com.java.springrestful.service.UserService;
import com.java.springrestful.util.annotation.ApiMessage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityService securityService;
    private final UserService userService;

    public AuthController(
            AuthenticationManagerBuilder authenticationManagerBuilder,
            SecurityService securityService,
            UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityService = securityService;
        this.userService = userService;
    }

    @PostMapping("/login")
    @ApiMessage("Login Success")
    public ResponseEntity<ResponseLoginDTO> login(@Valid @RequestBody LoginDTO loginDTO) {

        // nạp username và password vào security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());

        // xác thực người dùng
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String newToken = this.securityService.createToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User userDB = this.userService.handleGetUserByUsername(loginDTO.getUsername());
        ResponseLoginDTO responseLoginDTO = new ResponseLoginDTO();
        if (userDB != null) {
            ResponseLoginDTO.UserLogin userLogin = new ResponseLoginDTO.UserLogin(
                    userDB.getId(),
                    userDB.getName(),
                    userDB.getEmail());
            responseLoginDTO.setUserLogin(userLogin);
        }

        responseLoginDTO.setAccessToken(newToken);

        return ResponseEntity.ok().body(responseLoginDTO);
    }
}
