package com.myblog.myblog.mapper;

import com.myblog.myblog.entity.Archive;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArchiveMapper {

    /* Create */
    @Insert("INSERT INTO archives " +
            "(archiveName, postId) " +
            "VALUES (#{archiveName}, #{postId})")
    int save(Archive archive);

    /* Read */
    @Select("SELECT COUNT(postId) " +
            "FROM archives " +
            "WHERE archiveName = #{archiveName}")
    int getArchiveCountByName (String archiveName);

    @Select("SELECT DISTINCT archiveName " +
            "FROM archives " +
            "ORDER BY id DESC")
    List<String> findAllArchiveNames();

    /* Update */
    /* Archive name is string of year and month. Shouldn't update the name */

    /* Delete */
    @Delete("DELETE FROM archives " +
            "WHERE archiveId = #{archiveId}")
    int deleteArchive (Archive archive);

    @Delete("DELETE FROM archives " +
            "WHERE postId = #{postId}")
    int deleteArchiveByPostId(int postId);

}
