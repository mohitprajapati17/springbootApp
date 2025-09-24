package com.example.blog.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    public  String username ;
    public  String password;

    public String email;
}
