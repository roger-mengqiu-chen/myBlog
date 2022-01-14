package com.myblog.myblog.controller;

import com.myblog.myblog.request.PostRequest;
import com.myblog.myblog.response.JsonResponse;
import com.myblog.myblog.service.PostService;
import com.myblog.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping("/user/delete/{userId}")
    public JsonResponse deleteUserById(@PathVariable int userId) {
        return userService.deleteUserById(userId);
    }

    @GetMapping("/user/delete/{username}")
    public JsonResponse deleteUserByUsername(@PathVariable String username) {
        return userService.deleteUser(username);
    }

    @PostMapping("/post/")
    public JsonResponse createPost(@RequestBody PostRequest postRequest) {

        return null;
    }


}
