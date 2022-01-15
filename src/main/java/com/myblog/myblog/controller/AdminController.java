package com.myblog.myblog.controller;

import com.myblog.myblog.request.PostRequest;
import com.myblog.myblog.response.JsonResponse;
import com.myblog.myblog.service.PostService;
import com.myblog.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @PostMapping("/post")
    public JsonResponse createPost(@RequestBody PostRequest postRequest) {
        String title = postRequest.getTitle();
        String content = postRequest.getContent();
        String category = postRequest.getCategory();
        LocalDate publishDate = LocalDate.now();
        String excerpt = postRequest.getExcerpt();
        List<String> tags = postRequest.getTags();
        return postService.makePost(title, content, excerpt, category, publishDate, tags);
    }


}
