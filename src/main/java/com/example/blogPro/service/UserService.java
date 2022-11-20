package com.example.blogPro.service;

import com.example.blogPro.DTO.UserDTO;

public interface UserService {

    UserDTO updateUser(UserDTO userDTO, long id);
    boolean authenticateUserDetails(UserDTO userDTO,long id);

}
