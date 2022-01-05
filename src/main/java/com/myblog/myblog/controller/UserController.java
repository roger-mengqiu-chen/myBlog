package com.myblog.myblog.controller;

import com.myblog.myblog.entity.User;
import com.myblog.myblog.request.CreateUserRequest;
import com.myblog.myblog.request.LoginRequest;
import com.myblog.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping("/create")
    public ResponseEntity<User> createUser (@RequestBody CreateUserRequest createUserRequest) {
        String username = createUserRequest.getUsername();
        String password = createUserRequest.getPassword();
        String email = createUserRequest.getEmail();
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setEmail(email);
        try {
            userService.createUser(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername (@PathVariable String username) {
        User user = userService.findUserByName(username);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<User> modifyUser(@PathVariable Long userId, String username, String password, String email) {
        //TODO
        return null;
    }
}
