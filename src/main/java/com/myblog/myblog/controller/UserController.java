package com.myblog.myblog.controller;

import com.myblog.myblog.constant.Status;
import com.myblog.myblog.request.ModifyUserRequest;
import com.myblog.myblog.response.JsonResponse;
import com.myblog.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public JsonResponse getUserByUsername (@PathVariable String username) {
        return userService.findUserByUsername(username);
    }

    @PostMapping("/modify/{username}")
    public JsonResponse modifyUser(@PathVariable String username, @RequestBody ModifyUserRequest modifyUserRequest) {
        int userId = userService.getUserIdByName(username);
        String name = modifyUserRequest.getUsername();
        String email = modifyUserRequest.getEmail();
        String password = modifyUserRequest.getPassword();
        String avatarUrl = modifyUserRequest.getAvatarUrl();
        return userService.modifyUser(userId, name, encoder.encode(password), email,avatarUrl);
    }

    @GetMapping("/delete/{username}")
    public JsonResponse deleteUser(@PathVariable String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<SimpleGrantedAuthority> roles = new ArrayList(auth.getAuthorities());
        String role = roles.get(0).getAuthority();
        // USER can only delete itself, ADMIN can delete any user
        if (auth.getName().equals(username) || role.equals("ROLE_ADMIN")) {
            return userService.deleteUser(username);
        }
        else {
            return new JsonResponse(Status.PERMISSION_DENIED);
        }
    }



}
