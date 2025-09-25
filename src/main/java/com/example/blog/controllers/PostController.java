package com.example.blog.controllers;

import com.example.blog.models.Post;
import com.example.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    public PostService postService;

    @GetMapping("posts")
    ResponseEntity<List<Post >> showPosts(){
        return ResponseEntity.ok(postService.shows());
    }

    @GetMapping("post{id}")
    ResponseEntity<Post> showPost(@PathVariable Long id){
        return ResponseEntity.ok(postService.show(id));
    }

    @PostMapping("post")
    ResponseEntity<Post> createPost(@RequestBody Post post){
        return ResponseEntity.ok(postService.save(post));
    }

    @PostMapping("update")
    ResponseEntity<Post>  UpdatePost(@RequestBody Post post){
        return ResponseEntity.ok(postService.update(post));
    }

    @GetMapping("delete{id}")
    ResponseEntity<Post> DeletePost(@PathVariable Long id){
        return ResponseEntity.ok(postService.delete(id));
    }
    


    
}
