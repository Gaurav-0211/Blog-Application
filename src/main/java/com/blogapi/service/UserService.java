package com.blogapi.service;

import com.blogapi.payload.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(Integer userId, UserDto userDto);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUser();
    ResponseEntity<Void> deleteUser(Integer userId);

}
