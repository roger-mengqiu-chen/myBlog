package com.myblog.myblog.mapper;

import com.myblog.myblog.entity.Post;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PostMapper {
    /* Create */
    @Insert("INSERT INTO post (title, content, categoryId, publishDate, excerpt, lastPostId, nextPostId) " +
            "VALUES (#{title}, #{content}, #{categoryId}, #{publishDate}, #{excerpt}, #{lastPostId}, #{nextPostId})")
    int savePost(Post post);

    /* Read */
    @Select("SELECT * FROM post WHERE postId = #{postId}")
    @Results({
            @Result(id = true, property = "postId", column = "postId"),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "categoryId", column = "categoryId"),
            @Result(property = "publishDate", column = "publishDate"),
            @Result(property = "excerpt", column = "excerpt"),
            @Result(property = "lastPostId", column = "lastPostId"),
            @Result(property = "nextPostId", column = "nextPostId")
    })
    Post getPostById(Integer postId);

    @Select("SELECT * FROM post " +
            "WHERE title = #{title}")
    @Results({
            @Result(id = true, property = "postId", column = "postId"),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "categoryId", column = "categoryId"),
            @Result(property = "publishDate", column = "publishDate"),
            @Result(property = "excerpt", column = "excerpt"),
            @Result(property = "lastPostId", column = "lastPostId"),
            @Result(property = "nextPostId", column = "nextPostId")
    })
    Post getPostByTitle(String title);

    @Select("SELECT * FROM post p JOIN categories c " +
            "WHERE p.categoryId = c.categoryId " +
            "   AND c.categoryName = #{category} " +
            "ORDER BY postId DESC " +
            "LIMIT #{limit}, 10")
    @Results({
            @Result(id = true, property = "postId", column = "postId"),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "categoryId", column = "categoryId"),
            @Result(property = "publishDate", column = "publishDate"),
            @Result(property = "excerpt", column = "excerpt"),
            @Result(property = "lastPostId", column = "lastPostId"),
            @Result(property = "nextPostId", column = "nextPostId")
    })
    List<Post> getPostByCategory(String category, int limit);

    @Select("SELECT * FROM post p JOIN post_tags pt JOIN tags t " +
            "WHERE p.id = pt.postId " +
            "   AND pt.tag_id = t.id " +
            "   AND t.tagName = #{tagName} " +
            "ORDER BY postId DESC " +
            "LIMIT #{limit}, 10")
    @Results({
            @Result(id = true, property = "postId", column = "postId"),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "categoryId", column = "categoryId"),
            @Result(property = "publishDate", column = "publishDate"),
            @Result(property = "excerpt", column = "excerpt"),
            @Result(property = "lastPostId", column = "lastPostId"),
            @Result(property = "nextPostId", column = "nextPostId")
    })
    List<Post> getPostByTagName(String tagName, int limit);

    @Select("SELECT * FROM post " +
            "LIMIT #{limit}, 10")
    @Results({
            @Result(id = true, property = "postId", column = "postId"),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "categoryId", column = "categoryId"),
            @Result(property = "publishDate", column = "publishDate"),
            @Result(property = "excerpt", column = "excerpt"),
            @Result(property = "lastPostId", column = "lastPostId"),
            @Result(property = "nextPostId", column = "nextPostId")
    })
    List<Post> getAllPosts(int limit);

    @Select("SELECT * FROM post ORDER BY postId DESC LIMIT 1")
    @Results({
            @Result(id = true, property = "postId", column = "postId"),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "categoryId", column = "categoryId"),
            @Result(property = "publishDate", column = "publishDate"),
            @Result(property = "excerpt", column = "excerpt"),
            @Result(property = "lastPostId", column = "lastPostId"),
            @Result(property = "nextPostId", column = "nextPostId")
    })
    Post getLatestPost();

    @Select("SELECT COUNT(*) FROM post")
    int getAmountOfPosts();

    @Select("SELECT COUNT(*) FROM post p LEFT JOIN categories c " +
            "ON p.categoryId = c.categoryId " +
            "WHERE c.categoryName = #{category}")
    int getAmountOfPostOfCategory(String category);

    @Select("SELECT COUNT(*) FROM post p LEFT JOIN categories c " +
            "ON p.categoryId = c.categoryId " +
            "WHERE c.categoryId = #{categoryId}")
    int getAmountOfPostOfCategoryId(int categoryId);

    @Select("SELECT COUNT(*) FROM post_tags pt LEFT JOIN tags t " +
            "ON pt.tagId = t.tagId " +
            "WHERE t.tagName = #{tag}")
    int getAmountOfPostOfTag(String tag);

    /* Update */
    @Update("UPDATE post " +
            "SET title = #{title}, content = #{content}, categoryId = #{categoryId}, publishDate = #{publishDate}, excerpt = #{excerpt}, lastPostId = #{lastPostId}, nextPostId = #{nextPostId} " +
            "WHERE postId = #{postId} ")
    int updatePost(Post post);

    /* Delete */
    @Delete("DELETE FROM post " +
            "WHERE postId = #{postId}")
    int deletePost(Post post);

    @Delete("DELETE FROM post " +
            "WHERE categoryId = #{categoryId}")
    int deletePostsByCategory(int categoryId);

    @Delete("DELETE FROM post " +
            "WHERE postId IN (" +
            "      SELECT postId " +
            "      FROM post_tags" +
            "      WHERE tagId = #{tagId})")
    int deletePostsByTag(int tagId);
}
