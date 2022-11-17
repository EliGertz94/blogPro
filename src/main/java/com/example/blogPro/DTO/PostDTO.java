package com.example.blogPro.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;


@Data
public class PostDTO {

    private long id;
    @NotEmpty
    @Size(min = 2, message = " post title should be longer than 2 chars")
    private String title;
    @NotEmpty
    @Size(min = 10, message = " post description should be longer than 10 chars")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDTO> comments;
}
