package com.blogapi.payload;

import com.blogapi.entity.Category;
import com.blogapi.entity.User;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private String title;

    private String content;

    private Date addedDate;

    private String imageName;

    private UserDto user;

    private CategoryDto category;



}
