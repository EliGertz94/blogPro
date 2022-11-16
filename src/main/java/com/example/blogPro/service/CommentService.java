package com.example.blogPro.service;

import com.example.blogPro.DTO.CommentDTO;

import java.util.List;

public interface CommentService
{
    CommentDTO createComment(long postId,CommentDTO commentDTO);
    List<CommentDTO> getAllComments();

    List<CommentDTO> getByPostID(long id);

    List<CommentDTO> commentsByEmail(String email);

    CommentDTO getCommentByPostId(long postId,long commentId);

    CommentDTO updateCommentByPostId(long postID, long CommentId, CommentDTO commentDTO);

    String deleteCommentByPostId(long postID, long CommentId);

}
