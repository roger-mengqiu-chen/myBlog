package com.myblog.myblog.controller;

import com.myblog.myblog.entity.Post;
import com.myblog.myblog.response.JsonResponse;
import com.myblog.myblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/{pageNo}")
    public JsonResponse findAllPosts(@PathVariable int pageNo) {
        return  postService.findAllPostOnPage(pageNo);
    }
}
