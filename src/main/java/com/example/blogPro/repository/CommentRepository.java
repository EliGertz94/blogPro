package com.example.blogPro.repository;

import com.example.blogPro.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPostId(long id);
    List<Comment> findByEmail(String email);



}
