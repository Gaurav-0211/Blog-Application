package com.blogapi.controller;

import com.blogapi.config.AppConstants;
import com.blogapi.payload.UserDto;
import com.blogapi.payload.UserResponse;
import com.blogapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto userDto){
        UserDto userDto1 = this.service.createUser(userDto);
        return new ResponseEntity<>(userDto1,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer id){
        return ResponseEntity.ok(this.service.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id,@Valid @RequestBody UserDto userDto){
        UserDto updatedUser = this.service.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping
    public ResponseEntity<UserResponse> getAll(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam (value = "sortDir", defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
    ){
        UserResponse userDtos = this.service.getAllUser(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(userDtos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){
       return service.deleteUser(id);
    }

}
