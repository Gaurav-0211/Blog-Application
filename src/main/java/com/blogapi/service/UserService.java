package com.blogapi.service;

import com.blogapi.payload.UserDto;
import com.blogapi.payload.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);
    UserDto updateUser(Integer userId, UserDto userDto);
    UserDto getUserById(Integer userId);
    UserResponse getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    ResponseEntity<Void> deleteUser(Integer userId);

}
