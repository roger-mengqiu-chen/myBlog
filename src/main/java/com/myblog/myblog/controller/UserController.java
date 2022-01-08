package com.myblog.myblog.controller;

import com.myblog.myblog.entity.User;
import com.myblog.myblog.request.ModifyUserRequest;
import com.myblog.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('ROLE_USER')")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername (@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @PostMapping("/modify/{username}")
    public ResponseEntity<User> modifyUser(@PathVariable String username, @RequestBody ModifyUserRequest modifyUserRequest) {
        int userId = userService.getUserIdByName(username);
        String name = modifyUserRequest.getUsername();
        String email = modifyUserRequest.getEmail();
        String password = modifyUserRequest.getPassword();
        String avatarUrl = modifyUserRequest.getAvatarUrl();
        User user = userService.modifyUser(userId, name, encoder.encode(password), email,avatarUrl);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @GetMapping("/delete/{username}")
    public ResponseEntity deleteUser(@PathVariable String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<SimpleGrantedAuthority> roles = new ArrayList(auth.getAuthorities());
        String role = roles.get(0).getAuthority();
        // USER can only delete itself, ADMIN can delete any user
        if (auth.getName().equals(username) || role.equals("ROLE_ADMIN")) {
            userService.deleteUser(username);
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.status(401).build();
        }
    }



}
