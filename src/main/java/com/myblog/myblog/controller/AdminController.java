package com.myblog.myblog.controller;

import com.myblog.myblog.entity.User;
import com.myblog.myblog.request.PostRequest;
import com.myblog.myblog.request.UpdateAdminRequest;
import com.myblog.myblog.response.JsonResponse;
import com.myblog.myblog.service.PostService;
import com.myblog.myblog.service.UserService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @PostMapping("/update")
    public JsonResponse updateAdmin(@RequestBody UpdateAdminRequest updateAdminRequest) {

        String password = updateAdminRequest.getPassword();
        String email = updateAdminRequest.getEmail();
        String avatarUrl = updateAdminRequest.getAvatarUrl();
        User admin = userService.getUserByName("admin");
        admin.setPassword(password);
        admin.setEmail(email);
        admin.setAvatarUrl(avatarUrl);
        return userService.modifyUser(admin);
    }

    @GetMapping("/user/delete/{userId}")
    public JsonResponse deleteUserById(@PathVariable int userId) {
        return userService.deleteUserById(userId);
    }

    @GetMapping("/user/delete/{username}")
    public JsonResponse deleteUserByUsername(@PathVariable String username) {
        return userService.deleteUser(username);
    }

    @PostMapping("/post/create")
    public JsonResponse createPost(@RequestBody PostRequest postRequest) {
        String title = postRequest.getTitle();
        String content = postRequest.getContent();
        String category = postRequest.getCategory();
        String excerpt = postRequest.getExcerpt();
        List<String> tags = postRequest.getTags();
        return postService.makePost(title, content, excerpt, category, tags);
    }

    @PostMapping("/post/update")
    public JsonResponse updatePost(@RequestBody PostRequest postRequest) {
        Integer postId = postRequest.getPostId();
        String title = postRequest.getTitle();
        String content = postRequest.getContent();
        String category = postRequest.getCategory();
        String excerpt = postRequest.getExcerpt();
        List<String> tags = postRequest.getTags();
        return postService.updatePost(postId, title, content, excerpt, category, tags);
    }

    @GetMapping("/post/delete/{postId}")
    public JsonResponse deletePost(@PathVariable int postId) {
        return postService.deletePost(postId);
    }

}
