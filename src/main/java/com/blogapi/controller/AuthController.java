package com.blogapi.controller;

import com.blogapi.payload.JwtAuthRequest;
import com.blogapi.payload.UserDto;
import com.blogapi.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    @Autowired
    private JwtTokenHelper helper;

    @Autowired
    private UserDetailsService service;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtAuthRequest authRequest) {
        // handle JWT authentication and return token
        this.authenticate(authRequest.getUsername(),authRequest.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        // register a new user
        return null;
    }

//    private void authenticate(String username, String password){
//        this.manager.authenticate()
//    }
}

