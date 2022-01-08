package com.myblog.myblog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myblog.myblog.entity.User;
import com.myblog.myblog.request.CreateUserRequest;
import com.myblog.myblog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    private final static Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @PostMapping()
    public ResponseEntity createUser (@RequestBody CreateUserRequest createUserRequest) {

        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(encoder.encode(createUserRequest.getPassword()));
        user.setAvatarUrl(createUserRequest.getAvatarUrl());
        user.setEmail(createUserRequest.getEmail());
        user.setRoleId(1);
        int result = userService.createUser(user);
        if (result ==200) {
            return ResponseEntity.ok().build();
        }
        else if (result == 400){
            return ResponseEntity.badRequest().body("User already existed");
        } else {
            return ResponseEntity.status(500).body("Server error");
        }
    }
}
