package com.example.blog.service;

import com.example.blog.models.Post;
import com.example.blog.models.Users;
import com.example.blog.repo.PostRepository;
import com.example.blog.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService  {

    @Autowired
    public PostRepository postRepository;
    @Autowired
    public UserRepository userRepository;

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

        String username = post.getAuthor().getUsername();
        Users author = userRepository.findById(Long.valueOf(username))
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Set the persisted author
        post.setAuthor(author);

        // Set timestamps
        if (post.getCreatedAt() == null) post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public Post delete(Long id){
        Post existing = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        postRepository.delete(existing);
        return existing;
    }
    
}
