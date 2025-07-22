package com.blogapi.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    private Set<CommentDto> comments = new HashSet<>();

}
