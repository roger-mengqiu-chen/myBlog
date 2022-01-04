package com.myblog.myblog.service;

import com.myblog.myblog.entity.User;
import com.myblog.myblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }


    public void modifyUser(String username, String password) {

    }

    public void deleteUser(String username) {

    }

    public User findUserByName (String username) {
        return userRepository.findByUsername(username);
    }
}
