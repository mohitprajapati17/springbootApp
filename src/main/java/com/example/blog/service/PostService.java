package com.example.blog.service;

import com.example.blog.models.Post;
import com.example.blog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    public PostRepository postRepository;

    Post show(){
        return postRepository.findAll();
    }
}
