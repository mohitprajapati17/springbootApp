package com.example.blog.repo;

import com.example.blog.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {

    public Users findByUsername(String name);
}
