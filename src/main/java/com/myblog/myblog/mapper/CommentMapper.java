package com.myblog.myblog.mapper;

import com.myblog.myblog.entity.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {
    /* Create */
    @Insert("INSERT INTO comment (pId, postId, commenterId, commentDate, commentContent) " +
            "VALUES (#{pId}, #{postId}, #{commenterId}, #{commentDate}, #{commentContent})")
    int saveComment(Comment comment);

    /* Read */
    @Select("SELECT * FROM comment " +
            "WHERE commenterId = #{userId}")
    @Results({
            @Result(id = true, property = "commentId", column = "commentId"),
            @Result(property = "pId", column = "pId"),
            @Result(property = "postId", column = "postId"),
            @Result(property = "commenterId", column = "commenterId"),
            @Result(property = "commentDate", column = "commentDate"),
            @Result(property = "commentContent", column = "commentContent")
    })
    List<Comment> findAllCommentsOfUser (int userId);

    @Select("SELECT * FROM comment " +
            "WHERE pId = #{commentId}")
    @Results({
            @Result(id = true, property = "commentId", column = "commentId"),
            @Result(property = "pId", column = "pId"),
            @Result(property = "postId", column = "postId"),
            @Result(property = "commenterId", column = "commenterId"),
            @Result(property = "commentDate", column = "commentDate"),
            @Result(property = "commentContent", column = "commentContent")
    })
    List<Comment> findAllChildCommentOfAComment(int commentId);

    @Select("SELECT * FROM comment " +
            "WHERE postId = #{postId}")
    @Results({
            @Result(id = true, property = "commentId", column = "commentId"),
            @Result(property = "pId", column = "pId"),
            @Result(property = "postId", column = "postId"),
            @Result(property = "commenterId", column = "commenterId"),
            @Result(property = "commentDate", column = "commentDate"),
            @Result(property = "commentContent", column = "commentContent")
    })
    List<Comment> findAllCommentOfAPost(int postId);

    /* Update */
    @Update("UPDATE comment " +
            "SET commentDate = #{commentDate}, commentContent = #{commentContent} " +
            "WHERE commentId = #{commentId}")
    int updateComment(Comment comment);

    /* Delete */
    @Delete("DELETE FROM comment " +
            "WHERE commentId = #{commentId}")
    int deleteComment (Comment comment);
}
