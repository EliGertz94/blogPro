package com.example.blogPro.service.impl;

import com.example.blogPro.DTO.UserDTO;
import com.example.blogPro.entity.User;
import com.example.blogPro.exception.ResourceNotFound;
import com.example.blogPro.repository.UserRepository;
import com.example.blogPro.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    private ModelMapper modelMapper;

    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, long id) {


        //update user details
        return   userRepository.findById(id).map(user -> {
            user.setUserName(userDTO.getUserName());
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            return mapToDTO(userRepository.save(user));
                }
        ).orElseThrow(()-> new ResourceNotFound("user", "id",id));
    }

    // check if client exsit in db with username password and email

    public boolean authenticateUserDetails(UserDTO userDTO,long id){

        User user= userRepository.findById(id).orElseThrow(()-> new ResourceNotFound
                ("authenticateUserDetails","userId",id));


        if(!user.getEmail().equals(userDTO.getEmail())) {
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                return false;
            }
        }

        if(!user.getUserName().equals(userDTO.getUserName())) {
            if (userRepository.existsByUserName(userDTO.getUserName())) {
                return false;
            }
        }
        return true;
    }




    public UserDTO mapToDTO(User user){
        UserDTO userDTO=  modelMapper.map(user,UserDTO.class);

        return userDTO;
    }

    public User mapToEntity(UserDTO userDTO){
        User user=  modelMapper.map(userDTO,User.class);

        return user;
    }
}
