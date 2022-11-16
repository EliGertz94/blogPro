package com.example.blogPro.DTO;

import lombok.Data;
import lombok.ToString;


@Data
public class PostDTO {

    private long id;
    private String title;
    private String description;
    private String content;
}
