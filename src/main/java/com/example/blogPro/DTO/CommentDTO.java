package com.example.blogPro.DTO;

import com.example.blogPro.entity.Post;
import lombok.Data;

@Data
public class CommentDTO {

    private long id;
    private String name;
    private String email;
    private String body;


}
