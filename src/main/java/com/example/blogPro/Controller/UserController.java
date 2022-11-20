package com.example.blogPro.Controller;

import com.example.blogPro.DTO.UserDTO;
import com.example.blogPro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/update/{id}/")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id){
        System.out.println("userService.authenticateUserDetails(userDTO,id)"+userService.authenticateUserDetails(userDTO,id));
        if(!userService.authenticateUserDetails(userDTO,id)){

            return new ResponseEntity<>("try again details are not unique", HttpStatus.BAD_REQUEST);
        }
        userService.updateUser(userDTO,id);
        return new ResponseEntity<>("you are updated!",HttpStatus.OK);
    }
}
