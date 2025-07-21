package com.blogapi.service;

import com.blogapi.entity.Post;
import com.blogapi.payload.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    Post updatePost(PostDto postDto, Integer postId);

    void deletePost(Integer postId);

    List<Post> getAllPost();

    PostDto getPostById(Integer postId);

    List<PostDto> getPostByCategory(Integer categoryId);

    List<PostDto> getPostByUser(Integer userId);

    List<PostDto> searchPost(String keyword);
}
