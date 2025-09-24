package com.example.blog.controllers;

import com.example.blog.models.Users;
import com.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    public UserService userService;
    @PostMapping("signup")
    ResponseEntity<Users> register(@RequestBody Users user ){
        return ResponseEntity.ok(userService.save(user));


    }

}
