package com.myblog.myblog.controller;

import com.myblog.myblog.response.JsonResponse;
import com.myblog.myblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is open to public. Only has 1 method
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    public JsonResponse getAllCategories() {
        return categoryService.getAllCategories();
    }
}
