package com.blogapi.controller;

import com.blogapi.payload.UserDto;
import com.blogapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto){
        UserDto userDto1 = this.service.createUser(userDto);
        return new ResponseEntity<>(userDto1,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer id){
        return ResponseEntity.ok(this.service.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody UserDto userDto){
        UserDto updatedUser = this.service.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(){
        List<UserDto> userDtos = this.service.getAllUser();
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){
       return service.deleteUser(id);
    }

}
