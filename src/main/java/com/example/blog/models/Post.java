package com.example.blog.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Post entity representing a blog post
 * Maps to the 'post' table in the database
 */
@Entity
@Data  // Lombok annotation that generates getters, setters, toString, equals, and hashCode
public class Post {

    // Primary key with auto-generated ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    
    // Title of the blog post
    public String title;
    
    // Content of the blog post (stored as TEXT to allow long content)
    @Column(columnDefinition = "TEXT")
    public String content;

    // Many-to-One relationship: Many posts can belong to one user
    // This creates a foreign key relationship to the Users table
    @ManyToOne
    public Users author;
    
    // Timestamp when the post was created
    public LocalDateTime createdAt;
    
    // Timestamp when the post was last updated
    public LocalDateTime updatedAt;
}
