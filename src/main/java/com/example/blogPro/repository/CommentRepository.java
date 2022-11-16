package com.example.blogPro.repository;

import com.example.blogPro.DTO.CommentDTO;
import com.example.blogPro.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

  // @Query("from comment where post_id=post_id:post_id")
  // @Query(value = "SELECT * FROM Users u WHERE u.status = :status and u.name = :name",
  //        nativeQuery = true)
    List<Comment> findByPostId(long id);
    List<Comment> findByEmail(String email);

   // Comment findById(long id);

}
