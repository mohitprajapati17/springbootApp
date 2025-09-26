package com.example.blog.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.blog.models.Users;

/**
 * Custom UserDetails implementation for Spring Security
 * Wraps the Users entity to make it compatible with Spring Security authentication
 */
public class UserPrinciple implements UserDetails {

    // Reference to the Users entity
    private Users user;
    
    /**
     * Constructor that takes a Users entity
     * @param user - The Users entity to wrap
     */
    public UserPrinciple(Users user){
        this.user = user;
    }
    
    /**
     * Get user authorities/roles
     * @return Collection of granted authorities (roles)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return a single "USER" authority for all users
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }   

    /**
     * Get user's password
     * @return User's encrypted password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Get user's username
     * @return User's username
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Check if account is not expired
     * @return true (account never expires in this implementation)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Check if account is not locked
     * @return true (account is never locked in this implementation)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Check if credentials are not expired
     * @return true (credentials never expire in this implementation)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Check if account is enabled
     * @return true (account is always enabled in this implementation)
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
