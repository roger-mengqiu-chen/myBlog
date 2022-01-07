package com.myblog.myblog.controller;

import com.myblog.myblog.entity.User;
import com.myblog.myblog.request.CreateUserRequest;
import com.myblog.myblog.request.ModifyUserRequest;
import com.myblog.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping("/create")
    public ResponseEntity createUser (@RequestBody CreateUserRequest createUserRequest) {
        String username = createUserRequest.getUsername();
        String password = createUserRequest.getPassword();
        String email = createUserRequest.getEmail();
        String avatarUrl = createUserRequest.getAvatarUrl();
        User existedUser = userService.findUserByUsername(username);
        if (existedUser != null) {
            return ResponseEntity.badRequest().body("User existed!");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setEmail(email);
        user.setAvatarUrl(avatarUrl);

        try {
            userService.createUser(user, 1);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * For testing
     * TODO to be removed
     * @param createUserRequest
     * @return
     */
    @PostMapping("/create/admin")
    public ResponseEntity createAdmin(@RequestBody CreateUserRequest createUserRequest) {
        String username = createUserRequest.getUsername();
        String password = createUserRequest.getPassword();
        String email = createUserRequest.getEmail();
        String avatarUrl = createUserRequest.getAvatarUrl();
        User existedUser = userService.findUserByUsername(username);
        if (existedUser != null) {
            return ResponseEntity.badRequest().body("User existed!");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setEmail(email);
        user.setAvatarUrl(avatarUrl);

        try {
            userService.createUser(user, 2);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername (@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<User> modifyUser(@PathVariable Integer userId, @RequestBody ModifyUserRequest modifyUserRequest) {
        String username = modifyUserRequest.getUsername();
        String email = modifyUserRequest.getEmail();
        String password = modifyUserRequest.getPassword();
        String avatarUrl = modifyUserRequest.getAvatarUrl();
        User user = userService.modifyUser(userId, username, encoder.encode(password), email,avatarUrl);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @GetMapping("/delete/{username}")
    public ResponseEntity deleteUser(@PathVariable String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authorities: ");
        auth.getAuthorities().forEach(System.out::println);
        if (auth != null && (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
                                || ((auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")))
                                        && auth.getName().equals(username))) {
            userService.deleteUser(username);
            return ResponseEntity.ok().build();
        }
        else {
            System.out.println(auth.getAuthorities().size());
            return ResponseEntity.status(401).build();
        }
    }

}
