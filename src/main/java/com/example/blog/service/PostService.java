package com.example.blog.service;

import com.example.blog.models.Post;
import com.example.blog.models.Users;
import com.example.blog.repo.PostRepository;
import com.example.blog.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for handling blog post business logic
 * Provides methods for CRUD operations on blog posts
 */
@Service
public class PostService  {

    // Inject PostRepository to interact with post data
    @Autowired
    public PostRepository postRepository;
    
    // Inject UserRepository to interact with user data
    @Autowired
    public UserRepository userRepository;

    /**
     * Retrieve all blog posts from the database
     * @return List of all posts
     */
    public List<Post> shows(){
        // Use repository to find all posts in the database
        return postRepository.findAll();
    }

    /**
     * Retrieve a specific blog post by its ID
     * @param id - The ID of the post to retrieve
     * @return Post object if found, throws RuntimeException if not found
     */
    public Post show(Long id){
        // Find post by ID, throw exception if not found
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id " + id));
    }
    
    /**
     * Save a new blog post to the database
     * @param post - The post object to save
     * @param principal - The authentication principal to get the current user
     * @return The saved post object with generated ID
     */
    public Post save(Post post, Principal principal){
        // Get username from the authentication principal
        String username = principal.getName();

        // Find the user by ID (assuming username is used as ID)
        Users author = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Set the persisted author to ensure proper relationship
        post.setAuthor(author);

        // Set timestamps for tracking when post was created/updated
        if (post.getCreatedAt() == null) post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        // Save the updated post (update operation)
        return postRepository.save(post);
     }
     
    /**
     * Update an existing blog post
     * @param post - The post object with updated data
     * @param principal - The authentication principal to get the current user
     * @return The updated post object
     */
    public Post update(Post post, Principal principal){
        // Get username from the authentication principal
        String username = principal.getName();
        
        // Find the user by ID (assuming username is used as ID)
        Users author = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Set the persisted author to ensure proper relationship
        post.setAuthor(author);

        // Set timestamps for tracking when post was created/updated
        if (post.getCreatedAt() == null) post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        
        // Save the updated post (update operation)
        return postRepository.save(post);
    }

    /**
     * Delete a blog post by its ID
     * @param id - The ID of the post to delete
     * @return The deleted post object
     */
    public Post delete(Long id){
        // First find the post to ensure it exists
        Post existing = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        
        // Delete the post from database
        postRepository.delete(existing);
        
        // Return the deleted post object
        return existing;
    }
}
