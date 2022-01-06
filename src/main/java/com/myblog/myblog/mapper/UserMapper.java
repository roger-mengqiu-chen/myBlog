package com.myblog.myblog.mapper;

import com.myblog.myblog.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user " +
            "WHERE username = #{username}")
    User findUserByUsername(String username);

    @Insert("INSERT INTO user (username, password, email, avatarImgUrl) " +
            "VALUES (#{username}, #{password}, #{email}, #{avatarUrl})")
    void save(User user);

    @Select("SELECT * FROM user " +
            "WHERE id = #{id}")
    User findyUserById(int id);

    @Update("UPDATE user " +
            "SET username = #{username}, " +
            "    password = #{password}, " +
            "    email = #{email}, " +
            "    avatarImgUrl = #{avatarUrl} " +
            "WHERE id = #{id} ")
    void updateUser(User user);
}
