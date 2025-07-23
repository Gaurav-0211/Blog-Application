package com.blogapi.controller;

import com.blogapi.payload.JwtAuthRequest;
import com.blogapi.payload.JwtAuthResponse;
import com.blogapi.payload.UserDto;
import com.blogapi.security.JwtTokenHelper;
import com.blogapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtAuthRequest authRequest) throws Exception {
        // handle JWT authentication and return token
        this.authenticate(authRequest.getUsername(),authRequest.getPassword());
        UserDetails user =this.service.loadUserByUsername(authRequest.getUsername());
        String generateToken = this.helper.generateToken(user);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(generateToken);
        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        UserDto  registerNewUser=this.userService.registerNewUser(userDto);
        return new ResponseEntity<UserDto>(registerNewUser, HttpStatus.CREATED);
    }

    private void authenticate(String username, String password)throws Exception{
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        try {
            this.manager.authenticate(authenticationToken);
        }
        catch(BadCredentialsException e){
            System.out.println("Invalid Details");
            throw new  Exception("Invalid Username or password!!");
        }

    }
}

