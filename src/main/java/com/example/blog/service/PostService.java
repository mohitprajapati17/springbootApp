package com.example.blog.service;

import com.example.blog.models.Post;
import com.example.blog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService  {

    @Autowired
    public PostRepository postRepository;

    public List<Post> shows(){
        return postRepository.findAll();
    }

    public Post show(Long id){
        return  postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id " + id));

    }
    public Post save(Post post){
        return postRepository.save(post);

    }
    public Post update(Post post){
        return postRepository.save(post);
    }

    public Post delete(Long id){
        Post existing = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        postRepository.delete(existing);
        return existing;
    }
}
