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

    @GetMapping("/page/{pageNo}")
    public JsonResponse findAllPosts(@PathVariable int pageNo) {
        return  postService.findAllPostOnPage(pageNo);
    }

    @GetMapping("/{postId}")
    public JsonResponse getPostById(@PathVariable int postId) {
        return postService.getPostById(postId);
    }

    @GetMapping("/category/{category}/{pageNo}")
    public JsonResponse getPostByCategory(@PathVariable String category, @PathVariable int pageNo) {
        return postService.findAllPostOfCategory(category, pageNo);
    }

    @GetMapping("/tag/{tag}/{pageNo}")
    public JsonResponse getPostByTag (@PathVariable String tag, @PathVariable int pageNo) {
        return postService.findAllPostOfTag(tag, pageNo);
    }
}
