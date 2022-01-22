package com.myblog.myblog.controller;

import com.myblog.myblog.request.CommentRequest;
import com.myblog.myblog.response.JsonResponse;
import com.myblog.myblog.service.CommentService;
import com.myblog.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @PostMapping()
    public JsonResponse postComment(@RequestBody CommentRequest commentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int userId = userService.getUserIdByName(username);
        long pId = commentRequest.getPId();
        int postId = commentRequest.getPostId();
        String content = commentRequest.getContent();
        return commentService.postComment(userId, postId, pId, content);
    }

    @GetMapping("/ofuser{userId}")
    public JsonResponse getUserComment(@PathVariable Integer userId) {
        return commentService.getCommentsOfUser(userId);
    }

    @GetMapping("/ofpost{postId}")
    public JsonResponse getCommentOfPost(@PathVariable Integer postId) {
        return commentService.getCommentsOfPost(postId);
    }

    @GetMapping("/ofcomment{commentId}")
    public JsonResponse getRepliesOfComment(@PathVariable Long commentId) {
        return commentService.getChildCommentOfComment(commentId);
    }

}
