package com.myblog.myblog.service;

import com.myblog.myblog.constant.Status;
import com.myblog.myblog.entity.Comment;
import com.myblog.myblog.mapper.CommentMapper;
import com.myblog.myblog.mapper.PostMapper;
import com.myblog.myblog.mapper.UserMapper;
import com.myblog.myblog.response.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PostMapper postMapper;

    private final Logger logger = LoggerFactory.getLogger(CommentService.class);

    public JsonResponse postComment (Integer userId, Integer postId, Long pId, String content) {
        Comment comment = new Comment();
        comment.setCommenterId(userId);
        comment.setPostId(postId);
        comment.setPId(pId);
        comment.setCommentContent(content);
        comment.setCommentTime(LocalDateTime.now());
        try {
            commentMapper.saveComment(comment);
            logger.info("New comment post to {}", postId);
            return new JsonResponse(Status.SUCCESS, comment);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new JsonResponse(Status.SERVER_ERROR);
        }
    }

    public JsonResponse getCommentById(Long commentId) {
        return new JsonResponse(Status.SUCCESS, commentMapper.findCommentById(commentId));
    }

    public JsonResponse getCommentsOfPost(Integer postId) {
        List<Comment> comments = commentMapper.findAllCommentOfAPost(postId);
        return new JsonResponse(Status.SUCCESS, comments);
    }

    public JsonResponse getChildCommentOfComment(Long commentId) {
        List<Comment> comments = commentMapper.findAllChildCommentOfAComment(commentId);
        return new JsonResponse(Status.SUCCESS, comments);
    }

    public JsonResponse getCommentsOfUser (Integer userId) {
        List<Comment> comments = commentMapper.findAllCommentsOfUser(userId);
        return new JsonResponse(Status.SUCCESS, comments);
    }

    public JsonResponse deleteComment (Long commentId) {
        Comment comment = commentMapper.findCommentById(commentId);
        if (comment == null) {
            return new JsonResponse(Status.COMMENT_NOT_FOUND);
        }
        comment.setCommentContent("... The comment has been deleted ...");
        try {
            commentMapper.updateComment(comment);
            return new JsonResponse(Status.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new JsonResponse(Status.SERVER_ERROR);
        }
    }
}
