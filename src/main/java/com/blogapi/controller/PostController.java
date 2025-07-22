package com.blogapi.controller;

import com.blogapi.config.AppConstants;
import com.blogapi.payload.PostDto;
import com.blogapi.payload.PostResponse;
import com.blogapi.service.FileService;
import com.blogapi.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService service;
    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;
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
    public ResponseEntity<PostResponse> getAll(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                               @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                               @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                               @RequestParam (value = "sortDir", defaultValue = AppConstants.SORT_DIR,required = false) String sortDir){
        PostResponse posts = this.service.getAllPost(pageNumber, pageSize,sortBy, sortDir);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchByTitle(
            @PathVariable("keyword") String keyword){
        List<PostDto> result = this.service.searchPost(keyword);
        return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
    }


    // Post Image Upload
    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image,
                                                     @PathVariable Integer postId) throws IOException {
        PostDto postDto= this.service.getPostById(postId);
        String fileName = this.fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        PostDto updatedPost = this.service.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
    }

    // Method to serve Image
    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException{
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
