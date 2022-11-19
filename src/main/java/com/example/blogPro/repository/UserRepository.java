package com.example.blogPro.repository;

import com.example.blogPro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserName(String userName);

    Optional<User> findByUserNameOrEmail(String userName,String email);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);

}
