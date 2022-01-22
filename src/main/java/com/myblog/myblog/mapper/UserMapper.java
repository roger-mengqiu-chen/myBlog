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
    int save(User user);

    /* Read */
    @Select("SELECT * FROM user " +
            "ORDER BY userId " +
            "LIMIT #{limit}, 20")
    @Results({
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "avatarUrl", column = "avatarUrl"),
            @Result(property = "roleId", column = "roleId")
    })
    List<User> getAllUser(int limit);

    @Select("SELECT * FROM user " +
            "WHERE username = #{username}")
    @Results({
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "avatarUrl", column = "avatarUrl"),
            @Result(property = "roleId", column = "roleId")
    })
    User findUserByUsername(String username);

    @Select("SELECT * FROM user " +
            "WHERE email = #{email}")
    @Results({
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "avatarUrl", column = "avatarUrl"),
            @Result(property = "roleId", column = "roleId")
    })
    User findUserByEmail(String email);

    @Select("SELECT id FROM user " +
            "WHERE username = #{username}")
    int findUserId(String username);

    @Select("SELECT * FROM user " +
            "WHERE id = #{userId}")
    @Results({
            @Result(id = true, property = "userId", column = "userId"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "avatarUrl", column = "avatarUrl"),
            @Result(property = "roleId", column = "roleId")
    })
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
    int updateUser(User user);

    /* Delete */
    @Delete("DELETE FROM user " +
            "WHERE userId = #{userId}")
    int deleteUser(User user);

    @Delete("DELETE FROM user " +
            "WHERE userId = #{userId}")
    int deleteUsrById(int userId);
}
