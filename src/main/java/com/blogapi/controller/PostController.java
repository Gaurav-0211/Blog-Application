package com.blogapi.controller;

import com.blogapi.payload.PostDto;
import com.blogapi.payload.PostResponse;
import com.blogapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService service;
    //create
    @PostMapping("/user/{userId}/category/{categoryId}/post")
    public ResponseEntity<PostDto> create(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
        PostDto createPost = this.service.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
    }

    // Get By User
    @GetMapping("/user/{userId}/post")
    public ResponseEntity<List<PostDto>> getPostUser(@PathVariable Integer userId){
        List<PostDto> posts = this.service.getPostByUser(userId);
        return ResponseEntity.ok(posts);
    }

    //Get By Category

    @GetMapping("/category/{categoryId}/post")
    public ResponseEntity<List<PostDto>> getPostCategory(@PathVariable Integer categoryId){
        List<PostDto> posts = this.service.getPostByCategory(categoryId);
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updateUser(@RequestBody PostDto postDto,  @PathVariable Integer id){
        PostDto updatedPost = this.service.updatePost(postDto, id);
        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findById(@PathVariable Integer id){
        PostDto postDto = this.service.getPostById(id);
        return ResponseEntity.ok(postDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAll(@RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
                                               @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize){
        PostResponse posts = this.service.getAllPost(pageNumber, pageSize);
        return ResponseEntity.ok(posts);
    }


}
