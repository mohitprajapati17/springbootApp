package com.example.blog.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Users entity representing a blog user
 * Maps to the 'users' table in the database
 * Uses Lombok annotations for automatic getter/setter generation
 */
@Entity
@Data                    // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor       // Generates no-argument constructor
@AllArgsConstructor      // Generates constructor with all arguments
public class Users {
    
    // Primary key - username is used as the unique identifier
    @Id
    public String username;
    
    // User's password (will be encrypted before storing)
    public String password;

    // User's email address
    public String email;
    
    // User's role (defaults to "USER")
    public String roles = "USER";
}
