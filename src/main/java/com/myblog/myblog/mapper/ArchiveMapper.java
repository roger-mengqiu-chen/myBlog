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
            "(archiveName) " +
            "VALUES (#{archiveName})")
    int save(@Param("archiveName") String archiveName);

    /* Read */
    @Select("SELECT IFNULL(max(id),0) " +
            "FROM archives " +
            "WHERE archiveName=#{archiveName}")
    int findArchiveNameByArchiveName(@Param("archiveName") String archiveName);

    @Select("SELECT archiveName " +
            "FROM archives " +
            "ORDER BY id DESC")
    List<String> findAllArchiveNames();

    /* Update */
    @Update("UPDATE archives " +
            "SET archiveName = #{archiveName}")
    int updateArchive (Archive archive);

    /* Delete */
    @Delete("DELETE FROM archives " +
            "WHERE archiveId = #{archiveId}")
    int deleteArchive (Archive archive);

}
