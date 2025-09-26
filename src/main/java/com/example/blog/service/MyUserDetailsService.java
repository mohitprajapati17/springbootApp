package com.example.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.blog.models.Users;
import com.example.blog.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Custom UserDetailsService implementation for Spring Security
 * This service loads user details from database for authentication
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    // Inject UserRepository to access user data from database
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Load user details by username for Spring Security authentication
     * This method is called by Spring Security when authenticating users
     * 
     * @param username - The username to load user details for
     * @return UserDetails object containing user information for authentication
     * @throws UsernameNotFoundException if user is not found in database
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find user in database by username
        Users user = userRepository.findByUsername(username);
        
        // Check if user exists
        if(user == null){
            // Throw exception if user not found - this will cause authentication to fail
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        
        // Return custom UserPrinciple object that implements UserDetails
        // This wraps our Users entity in a Spring Security compatible format
        return new UserPrinciple(user);
    }
}
