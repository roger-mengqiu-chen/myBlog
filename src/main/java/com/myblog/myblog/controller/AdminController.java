package com.myblog.myblog.controller;

import com.myblog.myblog.constant.Status;
import com.myblog.myblog.entity.User;
import com.myblog.myblog.request.PostRequest;
import com.myblog.myblog.request.UpdateAdminRequest;
import com.myblog.myblog.response.JsonResponse;
import com.myblog.myblog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/update")
    public JsonResponse updateAdmin(@RequestBody UpdateAdminRequest updateAdminRequest) {

        String password = updateAdminRequest.getPassword();
        String email = updateAdminRequest.getEmail();
        String avatarUrl = updateAdminRequest.getAvatarUrl();
        User admin = userService.getUserByName("admin");
        admin.setPassword(password);
        admin.setEmail(email);
        admin.setAvatarUrl(avatarUrl);
        return userService.modifyUser(admin);
    }

    @GetMapping("/users/{pageNo}")
    public JsonResponse getUsers(@PathVariable int pageNo) {
        return userService.getAllUsers(pageNo);
    }

    @GetMapping("/users/delete/{userId}")
    public JsonResponse deleteUserById(@PathVariable int userId) {
        return userService.deleteUserById(userId);
    }

    @GetMapping("/users/delete/{username}")
    public JsonResponse deleteUserByUsername(@PathVariable String username) {
        return userService.deleteUser(username);
    }

    @PostMapping("/post/create")
    public JsonResponse createPost(@RequestBody PostRequest postRequest) {
        String title = postRequest.getTitle();
        String content = postRequest.getContent();
        String category = postRequest.getCategory();
        String excerpt = postRequest.getExcerpt();
        List<String> tags = postRequest.getTags();
        return postService.makePost(title, content, excerpt, category, tags);
    }

    @PostMapping("/post/update")
    public JsonResponse updatePost(@RequestBody PostRequest postRequest) {
        Integer postId = postRequest.getPostId();
        String title = postRequest.getTitle();
        String content = postRequest.getContent();
        String category = postRequest.getCategory();
        String excerpt = postRequest.getExcerpt();
        List<String> tags = postRequest.getTags();
        return postService.updatePost(postId, title, content, excerpt, category, tags);
    }

    @GetMapping("/post/delete/{postId}")
    public JsonResponse deletePost(@PathVariable int postId) {
        return postService.deletePost(postId);
    }

    @PostMapping("/category/create/{category}")
    public JsonResponse createCategory(@PathVariable String category) {
        return categoryService.createCategory(category);
    }

    public JsonResponse updateCategory(Integer categoryId, String categoryName){
        return categoryService.updateCategory(categoryId, categoryName);
    }

    @GetMapping("/category/delete/{category}")
    public JsonResponse deleteCategory(@PathVariable String category) {
        return categoryService.deleteCategory(category);
    }

    @GetMapping("/tag/delete/{tag}")
    public JsonResponse deleteTag(@PathVariable String tag) {
        return tagService.deleteTag(tag);
    }

    @PostMapping("/upload")
    public JsonResponse upload(@RequestBody MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return new JsonResponse(Status.FILE_EMPTY);
        }
        try {
            File uploadsDir = new File (ResourceUtils.getURL("classpath:").getPath());
            if (!uploadsDir.exists()){
                uploadsDir = new File("");
            }
            File upload = new File(uploadsDir.getAbsolutePath(), "static/imgs");
            if (!upload.exists()) {
                upload.mkdir();
            }
            String orgName = file.getOriginalFilename();
            String filePath = upload.getPath() + "/" + orgName;
            File dest = new File(filePath);

            file.transferTo(dest);
            return new JsonResponse(Status.SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonResponse(Status.SERVER_ERROR);
        }
    }

    @GetMapping("/files")
    public JsonResponse displayFileNames() {
        try {
            File classPath = new File(ResourceUtils.getURL("classpath:").getPath());
            File files = new File(classPath.getAbsolutePath(), "static/imgs");
            List<String> fileNames = new ArrayList<>();

            for (File f : files.listFiles()) {
                fileNames.add(f.getName());
            }

            return new JsonResponse(Status.SUCCESS, fileNames);
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonResponse(Status.SERVER_ERROR);
        }
    }

    @GetMapping("/files/delete/{file}")
    public JsonResponse delete(@PathVariable String file) {
        try {
            File classPath = new File(ResourceUtils.getURL("classpath:").getPath());
            File files = new File(classPath.getAbsolutePath(), "static/imgs");
            File fileToBeDeleted = new File (files + "/" + file);
            if (fileToBeDeleted.delete()) {
                return new JsonResponse(Status.SUCCESS, file);
            }
            else {
                return new JsonResponse(Status.SERVER_ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonResponse(Status.SERVER_ERROR);
        }
    }

    @GetMapping("/feedbacks")
    public JsonResponse getAllFeedback() {
        return feedbackService.getFeedbacks();
    }

    @GetMapping("/feedbacks/delete/id{}")
    public JsonResponse deleteFeedbackById(Integer feedbackId) {
        return feedbackService.deleteFeedbackById(feedbackId);
    }

    @GetMapping("/feedbacks/delete/email{}")
    public JsonResponse deleteFeedbackByEmail(String email) {
        return feedbackService.deleteFeedbackByEmail(email);
    }

    @GetMapping("/feedbacks/delete")
    public JsonResponse deleteAllFeedback() {
        return feedbackService.deleteAllFeedback();
    }

}
