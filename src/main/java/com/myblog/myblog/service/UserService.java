package com.myblog.myblog.service;


import com.myblog.myblog.constant.Status;
import com.myblog.myblog.entity.User;
import com.myblog.myblog.mapper.UserMapper;
import com.myblog.myblog.response.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    public JsonResponse findUserByUsername(String username) {
        User user = userMapper.findUserByUsername(username);
        if (user != null) {
            return new JsonResponse(Status.SUCCESS, user);
        } else {
            return new JsonResponse(Status.USER_NOT_FOUND);
        }
    }

    public String findUserRole(String username) {
        return userMapper.findUserRole(username);
    }

    public int getUserIdByName (String username) {
        return userMapper.findUserId(username);
    }

    public JsonResponse createUser(User user) {
        User existedUserByName = userMapper.findUserByUsername(user.getUsername());
        User existedUserByEmail = userMapper.findUserByEmail(user.getEmail());

        if (existedUserByName == null && existedUserByEmail == null) {
            try {
                userMapper.save(user);
                logger.debug("Created a new user: {}", user.getUsername());
                user.setPassword("");
                return new JsonResponse(Status.SUCCESS, user);
            } catch (Exception e) {
                logger.error("Tried to create user: {}, but failed: {}", user.getUsername(), e.getMessage());
                return new JsonResponse(Status.SERVER_ERROR);
            }
        }
        else if (existedUserByName != null){
            logger.debug("Tried to create a new user but user existed: {}", user.getUsername());
            return new JsonResponse(Status.USER_NAME_EXISTED, existedUserByName.getUsername());
        } else {
            logger.debug("Tried to create a new user but user email existed: {}", user.getEmail());
            return new JsonResponse(Status.USER_EMAIL_EXISTED, existedUserByEmail.getEmail());
        }
    }

    public JsonResponse modifyUser(int userId, String username, String password, String email, String avatarUrl) {
        User user = userMapper.findUserById(userId);
        User existedUserByName = userMapper.findUserByUsername(username);
        if (existedUserByName != null) {
            logger.debug("Tried to update a user but username existed: {}", username);
            return new JsonResponse(Status.USER_NAME_EXISTED, existedUserByName.getUsername());
        }
        User existedUserByEmail = userMapper.findUserByEmail(email);
        if (existedUserByEmail != null) {
            logger.debug("Tried to update a user but email existed: {}", email);
            return new JsonResponse(Status.USER_EMAIL_EXISTED, existedUserByEmail.getEmail());
        }
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setAvatarUrl(avatarUrl);
        try {
            userMapper.updateUser(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new JsonResponse(Status.SERVER_ERROR);
        }
        user.setPassword("");
        logger.info("Updated a user: {}", user.getUserId());
        return new JsonResponse(Status.SUCCESS, user);
    }

    public JsonResponse deleteUser (String username) {
        User user = userMapper.findUserByUsername(username);
        if (user == null) {
            logger.debug("User does not exist: {}", username);
            return new JsonResponse(Status.USER_NOT_FOUND);
        }
        try {
            userMapper.deleteUser(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new JsonResponse(Status.SERVER_ERROR);
        }
        logger.info("User is deleted: {}", user.getUsername());
        return new JsonResponse(Status.SUCCESS);
    }

    public JsonResponse deleteUserById (int userId) {
        try {
            userMapper.deleteUsrById(userId);
            return new JsonResponse(Status.SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new JsonResponse(Status.SERVER_ERROR);
    }
}
