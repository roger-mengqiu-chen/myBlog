package com.myblog.myblog.mapper;

import com.myblog.myblog.entity.Tag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagMapper {
    /* Create */
    @Insert("INSERT INTO tags (tagName) " +
            "VALUES (#{name})")
    int createTag(String name);

    @Insert("INSERT INTO post_tags " +
            "VALUES (#{postId}, #{tagId})")
    int insertPostTag(int postId, int tagId);

    /* Read */
    @Select("SELECT * FROM tags")
    @Results({
            @Result(id = true, property = "tagId", column = "tagId"),
            @Result(property = "tagName", column = "tagName")
    })
    List<Tag> getAllTags();

    @Select("SELECT tagName FROM tags")
    List<String> getAllTagNames();

    @Select("SELECT * FROM tags " +
            "WHERE tagName = #{name}")
    @Results({
            @Result(id = true, property = "tagId", column = "tagId"),
            @Result(property = "tagName", column = "tagName")
    })
    Tag findTagByName (String name);

    @Select("SELECT * FROM tags " +
            "WHERE tagId = #{tagId}")
    @Results({
            @Result(id = true, property = "tagId", column = "tagId"),
            @Result(property = "tagName", column = "tagName")
    })
    Tag findTagById (int tagId);

    /* Update */
    @Update("UPDATE tags " +
            "SET tagName = #{tagName} " +
            "WHERE tagId = #{tagId}")
    int updateTag(Tag tag);

    /* Delete */
    @Delete("DELETE FROM tags " +
            "WHERE tagId = #{tagId}")
    int deleteTag(Tag tag);

    @Delete("DELETE FROM post_tags " +
            "WHERE postId = #{postId} " +
            "   AND tagId = #{tagId}")
    int deletePostTag(int postId, int tagId);

    @Delete("DELETE FROM post_tags " +
            "WHERE postId = #{postId}")
    int deleteTagOfPost(int postId);
}
