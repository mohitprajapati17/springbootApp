package com.example.blog.controllers;

import com.example.blog.models.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {


    @GetMapping("posts")
    ResponseEntity<Post> showPosts(){
        return ResponseEntity(postService.show());
    }
}
