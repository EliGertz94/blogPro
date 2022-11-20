package com.example.blogPro.Controller;

import com.example.blogPro.DTO.LoginDTO;
import com.example.blogPro.DTO.SignUpDTO;
import com.example.blogPro.entity.Role;
import com.example.blogPro.entity.User;
import com.example.blogPro.repository.RoleRepository;
import com.example.blogPro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    // check if login is correct

    @PostMapping("/signin")
    public ResponseEntity<String> authenticationUser(@RequestBody LoginDTO loginDTO){
       Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsernameOrEmail(),loginDTO.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("user signed in successfully", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDTO){

        //check if user is in DB

        if(userRepository.existsByUserName(signUpDTO.getUserName())){
          return  new ResponseEntity<String>("username is in use already use some other username",HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpDTO.getEmail())){
           return new ResponseEntity<String>("email is in use already use some other email",HttpStatus.BAD_REQUEST);
        }

        //save request body to the user object
        User user = new User();
        user.setUserName(signUpDTO.getUserName());
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        user.setName(signUpDTO.getName());

        Role role= roleRepository.findByName("ROLE_ADMIN").get();
    user.setRoles(Collections.singleton(role));
        userRepository.save(user);
        return new ResponseEntity<>("You are ready to Go Welcome!",HttpStatus.OK);
    }










}
