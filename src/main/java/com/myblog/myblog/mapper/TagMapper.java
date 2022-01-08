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

    /* Read */
    @Select("SELECT tagName FROM tags")
    List<String> getAllTags();

    @Select("SELECT * FROM tags " +
            "WHERE tagName = #{name}")
    @Results({
            @Result(id = true, property = "tagId", column = "tagId"),
            @Result(property = "tagName", column = "tagName")
    })
    Tag findTagByName (String name);

    /* Update */
    @Update("UPDATE tags " +
            "SET tagName = #{tagName} " +
            "WHERE tagId = #{tagId}")
    int updateTag(Tag tag);

    /* Delete */
    @Delete("DELETE FROM tags " +
            "WHERE tagId = #{tagId}")
    int deleteTag(Tag tag);
}
