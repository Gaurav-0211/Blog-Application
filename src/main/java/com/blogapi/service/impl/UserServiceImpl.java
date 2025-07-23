package com.blogapi.service.impl;

import com.blogapi.entity.User;
import com.blogapi.exceptions.ResourceNotFoundException;
import com.blogapi.payload.UserDto;
import com.blogapi.payload.UserResponse;
import com.blogapi.repo.UserRepo;
import com.blogapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User saved = this.userRepo.save(user);
        return this.userToDto(saved);
    }

    @Override
    public UserDto updateUser(Integer userId, UserDto userDto) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User update = this.userRepo.save(user);
        UserDto userDto1 = this.userToDto(update);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user  = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        return this.userToDto(user);
    }

    @Override
    public UserResponse getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = null;
        if(sortDir.equals("asc")){
            sort = Sort.by(sortBy).ascending();
        }else{
            sort = Sort.by(sortBy).descending();
        }

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> userPage = this.userRepo.findAll(p);
        List<User> allUsers = userPage.getContent();
        List<UserDto> userDtos = allUsers.stream().map(this::userToDto).collect(Collectors.toList());
        UserResponse userResponse = new UserResponse();
        userResponse.setContent(userDtos);
        userResponse.setPageNumber(userPage.getNumber());
        userResponse.setPageSize(userPage.getSize());
        userResponse.setTotalPage(userPage.getTotalPages());
        userResponse.setTotalElements(userPage.getTotalElements());
        userResponse.setLastPage(userPage.isLast());
        return userResponse;
    }

    @Override
    public ResponseEntity<Void> deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
        return null;
    }

    private User dtoToUser(UserDto userDto){
        User user = this.mapper.map(userDto,User.class);
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return user;
    }

    private UserDto userToDto(User user){
        UserDto userDto = this.mapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", "email: " + username, 0));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }
}
