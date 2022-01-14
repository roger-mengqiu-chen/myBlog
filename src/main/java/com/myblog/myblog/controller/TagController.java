package com.myblog.myblog.controller;

import com.myblog.myblog.constant.Status;
import com.myblog.myblog.response.JsonResponse;
import com.myblog.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is open to public, only has 1 mapping
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;


    @GetMapping()
    public JsonResponse getAllTags() {
        return tagService.getAllTags();
    }


}
