package com.example.blog.controllers;

import com.example.blog.models.Users;
import com.example.blog.service.JwtService;
import com.example.blog.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for handling user authentication and registration
 * Provides endpoints for user signup and login operations
 */
@RestController

@CrossOrigin("*")
public class UserController {

    // Inject AuthenticationManager to handle user authentication
    @Autowired
    public AuthenticationManager authenticationManager;
    
    // Inject JwtService to generate JWT tokens
    @Autowired 
    public JwtService jwtService;

    // Inject UserService to handle user business logic
    @Autowired
    public UserService userService;
    
    /**
     * POST endpoint for user registration/signup
     * URL: /signup
     * @param user - User object containing registration data from request body
     * @return ResponseEntity containing the created user
     */
    @PostMapping("signup")
    ResponseEntity<Users> register(@RequestBody Users user ){
        // Call service to save new user (password will be encrypted) and return as HTTP 200 OK response
        return ResponseEntity.ok(userService.save(user));
    }

    /**
     * POST endpoint for user login/authentication
     * URL: /login
     * @param users - User object containing login credentials from request body
     * @return JWT token string if authentication successful, error message otherwise
     */
    @PostMapping("login")
    public String login(@RequestBody Users users){
        try{
            // Create authentication token with username and password
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword())
            );
            
            // Check if authentication was successful
            if(authentication.isAuthenticated()){
                // Generate and return JWT token for authenticated user
                return jwtService.generateToken(users.getUsername());
            }
        }
        catch(AuthenticationException e){
            // Return error message if authentication fails
            return "invalid credentials";
        }

        // Return unauthorized message if authentication is not successful
        return "Unauthorized";
    }
}
