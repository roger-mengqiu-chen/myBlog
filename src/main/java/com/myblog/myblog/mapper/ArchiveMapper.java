package com.myblog.myblog.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArchiveMapper {

    @Select("SELECT archiveName " +
            "FROM archives " +
            "ORDER BY id DESC")
    List<String> findAllArchiveNames();

    @Insert("INSERT INTO archives " +
            "(archiveName) " +
            "VALUES (#{archiveName})")
    void save(@Param("archiveName") String archiveName);

    @Select("SELECT IFNULL(max(id),0) " +
            "FROM archives " +
            "WHERE archiveName=#{archiveName}")
    int findArchiveNameByArchiveName(@Param("archiveName") String archiveName);
}
