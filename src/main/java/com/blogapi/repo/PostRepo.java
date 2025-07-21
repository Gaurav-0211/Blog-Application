package com.blogapi.repo;

import com.blogapi.entity.Category;
import com.blogapi.entity.Post;
import com.blogapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
}
