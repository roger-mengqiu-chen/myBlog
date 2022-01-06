package com.myblog.myblog.service;


import com.myblog.myblog.entity.User;
import com.myblog.myblog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    public void createUser(User user) {
        userMapper.save(user);
    }

    public User modifyUser(int userId, String username, String password, String email, String avatarUrl) {
        User user = userMapper.findyUserById(userId);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setAvatarUrl(avatarUrl);
        userMapper.updateUser(user);
        return user;
    }

    public void deleteUser (String username) {
        User user = userMapper.findUserByUsername(username);
        userMapper.deleteUser(user);
    }
}
