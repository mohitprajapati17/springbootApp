package com.example.blog.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String title;
    @Column(columnDefinition = "TEXT")
    public String content;

    @ManyToOne
    public User author;
    public LocalDateTime createdAt;
    public LocalDateTime  updatedAt;



}
