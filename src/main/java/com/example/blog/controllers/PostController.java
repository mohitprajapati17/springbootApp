package com.example.blog.controllers;

import com.example.blog.models.Post;
import com.example.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for handling blog post operations
 * Provides endpoints for CRUD operations on blog posts
 */
@RestController
public class PostController {

    // Inject PostService to handle business logic
    @Autowired
    public PostService postService;

    /**
     * GET endpoint to retrieve all blog posts
     * URL: /posts
     * @return ResponseEntity containing list of all posts
     */
    @GetMapping("posts")
    ResponseEntity<List<Post>> showPosts(){
        // Get current authentication context for debugging
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated: " + auth.isAuthenticated());
        System.out.println("Authorities: " + auth.getAuthorities());
        
        // Call service to get all posts and return as HTTP 200 OK response
        return ResponseEntity.ok(postService.shows());
    }

    /**
     * GET endpoint to retrieve a specific blog post by ID
     * URL: /post{id}
     * @param id - The ID of the post to retrieve
     * @return ResponseEntity containing the requested post
     */
    @GetMapping("post{id}")
    ResponseEntity<Post> showPost(@PathVariable Long id){
        // Call service to find post by ID and return as HTTP 200 OK response
        return ResponseEntity.ok(postService.show(id));
    }

    /**
     * POST endpoint to create a new blog post
     * URL: /post
     * @param post - The post object containing post data from request body
     * @return ResponseEntity containing the created post
     */
    @PostMapping("post")
    ResponseEntity<Post> createPost(@RequestBody Post post){
        // Call service to save new post and return as HTTP 200 OK response
        return ResponseEntity.ok(postService.save(post));
    }

    /**
     * POST endpoint to update an existing blog post
     * URL: /update
     * @param post - The post object with updated data from request body
     * @return ResponseEntity containing the updated post
     */
    @PostMapping("update")
    ResponseEntity<Post> UpdatePost(@RequestBody Post post){
        // Call service to update existing post and return as HTTP 200 OK response
        return ResponseEntity.ok(postService.update(post));
    }

    /**
     * GET endpoint to delete a blog post by ID
     * URL: /delete{id}
     * @param id - The ID of the post to delete
     * @return ResponseEntity containing the deleted post
     */
    @GetMapping("delete{id}")
    ResponseEntity<Post> DeletePost(@PathVariable Long id){
        // Call service to delete post by ID and return deleted post as HTTP 200 OK response
        return ResponseEntity.ok(postService.delete(id));
    }
}
