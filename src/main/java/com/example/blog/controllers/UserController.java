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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    public AuthenticationManager authenticationManager;
    @Autowired 
    public JwtService jwtService;

    @Autowired
    public UserService userService;
    @PostMapping("signup")
    ResponseEntity<Users> register(@RequestBody Users user ){
        return ResponseEntity.ok(userService.save(user));
    }

    @PostMapping("login")
    public String  login(@RequestBody Users users){
        try{
            Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));
            if(authentication.isAuthenticated()){
                return jwtService.generateToken(users.getUsername());
            }


        }
        catch(AuthenticationException e){
            return "invalid credentials";
        }


        return "Unauthorized";

    }

}
