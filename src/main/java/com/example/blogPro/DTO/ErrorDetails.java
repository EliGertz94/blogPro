package com.example.blogPro.DTO;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class ErrorDetails {

    private Date timeStamp;
    private String message;
    private String details;


}
