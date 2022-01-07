package com.myblog.myblog.mapper;

import com.myblog.myblog.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    /* Create */
    @Insert("INSERT INTO user (username, password, email, avatarUrl, roleId) " +
            "VALUES (#{username}, #{password}, #{email}, #{avatarUrl}, #{roleId})")
    void save(User user);

    /* Read */
    @Select("SELECT * FROM user")
    List<User> getAllUser();

    @Select("SELECT * FROM user " +
            "WHERE username = #{username}")
    User findUserByUsername(String username);

    @Select("SELECT id FROM user " +
            "WHERE username = #{username}")
    int findUserId(String username);

    @Select("SELECT * FROM user " +
            "WHERE id = #{userId}")
    User findUserById(int userId);

    @Select("SELECT roleName FROM user u JOIN role r " +
            "WHERE u.roleId = r.roleId " +
            "   AND u.username = #{username}")
    String findUserRole(@Param("username") String username);

    /* Update */
    @Update("UPDATE user " +
            "SET username = #{username}, " +
            "    password = #{password}, " +
            "    email = #{email}, " +
            "    avatarUrl = #{avatarUrl} " +
            "WHERE userId = #{userId} ")
    void updateUser(User user);

    /* Delete */
    @Delete("DELETE FROM user " +
            "WHERE userId = #{userId}")
    void deleteUser(User user);

    @Delete("DELETE FROM user " +
            "WHERE userId = #{userId}")
    void deleteUsrById(int userId);
}
