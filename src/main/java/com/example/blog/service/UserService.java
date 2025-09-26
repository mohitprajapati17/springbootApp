package com.example.blog.service;

import com.example.blog.models.Users;
import com.example.blog.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for handling user business logic
 * Provides methods for user registration and authentication
 */
@Service
public class UserService {

    // Inject UserRepository to interact with user data
    @Autowired
    public UserRepository userRepository;
    
    // Create BCrypt password encoder with strength 12 for secure password hashing
    public BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    /**
     * Save a new user to the database with encrypted password
     * @param user - The user object to save
     * @return The saved user object with encrypted password
     */
    public Users save(Users user){
        // Encrypt the user's password before saving to database
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        
        // Save the user with encrypted password to database
        return userRepository.save(user);
    }
    
    /**
     * Find a user by their username
     * @param name - The username to search for
     * @return User object if found, null if not found
     */
    public Users findUserByUsername(String name){
        // Use repository to find user by username
        return userRepository.findByUsername(name);
    }
}
