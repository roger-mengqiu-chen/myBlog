package com.myblog.myblog.mapper;

import com.myblog.myblog.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user " +
            "WHERE username = #{username}")
    User findUserByUsername(String username);

    @Select("SELECT id FROM user " +
            "WHERE username = #{username}")
    int findUserId(String username);

    @Insert("INSERT INTO user (username, password, email, avatarImgUrl) " +
            "VALUES (#{username}, #{password}, #{email}, #{avatarUrl})")
    void save(User user);

    @Insert("INSERT INTO user_role (user_id, role_id) " +
            "VALUES (#{userId}, #{roleId})")
    void saveRole(@Param("userId") int userId, @Param("roleId") int roleId);

    @Select("SELECT * FROM user " +
            "WHERE id = #{id}")
    User findyUserById(int id);

    @Select("SELECT name FROM role r JOIN user_role ur JOIN user u " +
            "WHERE r.id = ur.role_id " +
            "   AND ur.user_id = u.id " +
            "   AND u.username = #{username}")
    String findUserRole(@Param("username") String username);

    @Update("UPDATE user " +
            "SET username = #{username}, " +
            "    password = #{password}, " +
            "    email = #{email}, " +
            "    avatarImgUrl = #{avatarUrl} " +
            "WHERE id = #{id} ")
    void updateUser(User user);

    @Delete("DELETE FROM user " +
            "WHERE id = #{id}")
    void deleteUser(User user);
}
