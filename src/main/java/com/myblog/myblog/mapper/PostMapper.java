package com.myblog.myblog.mapper;

import com.myblog.myblog.entity.Post;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PostMapper {
    /* Create */
    @Insert("INSERT INTO post (title, content, category, publishDate, excerpt, lastPostId, nextPostId) " +
            "VALUES (#{title}, #{content}, #{category}, #{publishDate}, #{excerpt}, #{lastPostId}, #{nextPostId})")
    int savePost(Post post);

    /* Read */
    @Select("SELECT * FROM post WHERE postId = {#postId}")
    @Results({
            @Result(id = true, property = "postId", column = "postId"),
            @Result(property = "content", column = "content"),
            @Result(property = "category", column = "category"),
            @Result(property = "publishDate", column = "publishDate"),
            @Result(property = "excerpt", column = "excerpt"),
            @Result(property = "lastPostId", column = "lastPostId"),
            @Result(property = "nextPostId", column = "nextPostId")
    })
    Post getPostById(int postId);

    @Select("SELECT * FROM post p JOIN categories c " +
            "WHERE p.categoryId = c.categoryId " +
            "   AND c.categoryName = #{category}")
    @Results({
            @Result(id = true, property = "postId", column = "postId"),
            @Result(property = "content", column = "content"),
            @Result(property = "category", column = "category"),
            @Result(property = "publishDate", column = "publishDate"),
            @Result(property = "excerpt", column = "excerpt"),
            @Result(property = "lastPostId", column = "lastPostId"),
            @Result(property = "nextPostId", column = "nextPostId")
    })
    List<Post> getPostByCategory(String category);


    @Select("SELECT * FROM post p JOIN post_tags pt JOIN tags t " +
            "WHERE p.id = pt.postId " +
            "   AND pt.tag_id = t.id " +
            "   AND t.tagName = #{tagName}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "content", column = "content"),
            @Result(property = "category", column = "category"),
            @Result(property = "publishDate", column = "publishDate"),
            @Result(property = "excerpt", column = "excerpt"),
            @Result(property = "lastPostId", column = "lastPostId"),
            @Result(property = "nextPostId", column = "nextPostId")
    })
    List<Post> getPostByTagName(String tagName);

    /* Update */
    @Update("UPDATE post " +
            "SET title = #{title}, content = #{content}, category = #{category}, publishDate = #{publishDate}, excerpt = #{excerpt}, lastPostId = #{lastPostId}, nextPostId = #{nextPostId} " +
            "WHERE id = #{postId} ")
    int updatePost(Post post);

    /* Delete */
    @Delete("DELETE FROM post " +
            "WHERE postId = #{postId}")
    int deletePost(Post post);



}
