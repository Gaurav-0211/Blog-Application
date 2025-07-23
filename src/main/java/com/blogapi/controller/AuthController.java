package com.blogapi.controller;

import com.blogapi.payload.JwtAuthRequest;
import com.blogapi.payload.JwtAuthResponse;
import com.blogapi.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
        UserDetails user =this.service.loadUserByUsername(authRequest.getUsername());
        String generateToken = this.helper.generateToken(user);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(generateToken);
        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        this.manager.authenticate(authenticationToken);

    }
}

