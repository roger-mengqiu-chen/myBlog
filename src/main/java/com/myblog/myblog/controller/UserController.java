package com.myblog.myblog.controller;

import com.myblog.myblog.entity.User;
import com.myblog.myblog.request.CreateUserRequest;
import com.myblog.myblog.request.ModifyUserRequest;
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
        String avatarUrl = createUserRequest.getAvatarUrl();
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setEmail(email);
        user.setAvatarUrl(avatarUrl);
        try {
            userService.createUser(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername (@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<User> modifyUser(@PathVariable Integer userId, @RequestBody ModifyUserRequest modifyUserRequest) {
        String username = modifyUserRequest.getUsername();
        String email = modifyUserRequest.getEmail();
        String password = modifyUserRequest.getPassword();
        String avatarUrl = modifyUserRequest.getAvatarUrl();
        User user = userService.modifyUser(userId, username, encoder.encode(password), email,avatarUrl);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }
}
