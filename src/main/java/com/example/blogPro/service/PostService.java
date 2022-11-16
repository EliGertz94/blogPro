package com.example.blogPro.service;

import com.example.blogPro.DTO.PostDTO;
import com.example.blogPro.DTO.PostResponse;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO);
    PostResponse listPosts(int pageNumber , int postNumber,String sortBy,String sortDit);
    PostDTO getPostById(long id);

    PostDTO updatePost(PostDTO postDTO, long id);

    void deletePost(long id);



}
