package com.myblog.myblog.controller;

import com.myblog.myblog.constant.Role;
import com.myblog.myblog.constant.Status;
import com.myblog.myblog.response.JsonResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequestMapping("/upload")
public class UploadController {

    @PostMapping
    public JsonResponse upload(@PathVariable String type, @RequestBody MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList(authentication.getAuthorities());
        String role = authorities.get(0).getAuthority();
        if (file == null || file.isEmpty()) {
            return new JsonResponse(Status.FILE_EMPTY);
        }
        try {
            File uploadsDir = new File (ResourceUtils.getURL("classpath:").getPath());

            File uploadToImg = new File(uploadsDir.getAbsolutePath(), "static/imgs");
            File uploadToAvatar = new File(uploadsDir.getAbsolutePath(), "static/avatar");
            String orgName = file.getOriginalFilename();
            String filePath = "";
            if (type.equals("img") && role.equals(Role.ADMIN)) {
                filePath = uploadToImg.getPath() + "/" + orgName;
            }
            else if (type.equals("avatar") && (role.equals(Role.ADMIN) || role.equals(Role.USER))) {
                filePath = uploadToAvatar.getPath() + "/" + orgName;
            }
            else if (type.equals("img") && !role.equals(Role.ADMIN)) {
                return new JsonResponse(Status.PERMISSION_DENIED);
            }

            if (!filePath.isEmpty()) {
                File dest = new File(filePath);
                file.transferTo(dest);
                return new JsonResponse(Status.SUCCESS);
            }
            else {
                return new JsonResponse(Status.UPLOADING_TYPE_ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonResponse(Status.SERVER_ERROR);
        }
    }
}
