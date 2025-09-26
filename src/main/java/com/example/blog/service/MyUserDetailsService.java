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

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //Doubt about why implement this when you can make your own
        Users user =userRepository.findByUsername(username);
        if(user==null){

            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return  new UserPrinciple(user);
    }


    
}
