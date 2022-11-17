package com.example.blogPro.DTO;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDTO {

    private long id;
    @NotEmpty
    @Size(min = 2, message = " post name should be longer than 2 chars")
    private String name;
    @NotEmpty
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty
    private String body;


}
