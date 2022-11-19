package com.example.blogPro.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderGenerator {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        System.out.println(passwordEncoder.encode("admin"));
        //$2a$10$WSy291fKCpvO87nt6693aOTkRVhntyJTgjmO9vhpK6UmbwSQLFlBm password
        //$2a$10$DOXeyu/4gRyWZmCTTtfeY.D6ckrhVBte2y.V0V1lZ11Qz4HTvON8i
        //$2a$10$LRha6rrN.Lxl8BGW0gHoHuPQNqcfmtpXOEDeF6jRFWniG0nMTz2nu
    }
}
