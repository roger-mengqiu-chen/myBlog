package com.myblog.myblog.service;

import com.myblog.myblog.constant.Status;
import com.myblog.myblog.entity.Category;
import com.myblog.myblog.mapper.CategoryMapper;
import com.myblog.myblog.response.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public JsonResponse createCategory(String categoryName) {
        categoryName = categoryName.trim().toLowerCase();
        Category existedCategory = categoryMapper.findCategoryByName(categoryName);
        if (existedCategory != null) {
            logger.debug("Tried to create a category but failed: category exsits: {}", categoryName);
            return new JsonResponse(Status.CATEGORY_EXIST);
        }
        try {
            categoryMapper.createCategory(categoryName);
            Category category = categoryMapper.findCategoryByName(categoryName);
            logger.info("Created a new category: {}", categoryName);
            return new JsonResponse(Status.SUCCESS, category);
        } catch (Exception e) {
            logger.error("Tried to create category but failed: {}", categoryName);
        }
        return new JsonResponse(Status.SERVER_ERROR);
    }

    public JsonResponse getAllCategories() {
        List<String> categories = categoryMapper.getAllCategories();
        return new JsonResponse(Status.SUCCESS, categories);
    }

    public JsonResponse updateCategory(Category category) {
        Integer categoryId = category.getCategoryId();
        if(categoryId == null || categoryMapper.findCategoryById(categoryId) == null) {
            logger.error("Tried to update category but failed: Category not found: {}", category.getCategoryName());
            return new JsonResponse(Status.CATEGORY_NOT_FOUND);
        } else {
            try {
                categoryMapper.updateCategory(category);
                logger.info("Category is updated: {}", category.getCategoryName());
                return new JsonResponse(Status.SUCCESS);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            return new JsonResponse(Status.SERVER_ERROR);
        }
    }

    public JsonResponse deleteCategory (String categoryName) {
        Category category = categoryMapper.findCategoryByName(categoryName);
        if (category == null) {
            logger.debug("Tried to delete category but failed: category not found: {}", categoryName);
            return new JsonResponse(Status.CATEGORY_NOT_FOUND);
        }
        else {
            try {
                categoryMapper.deleteCategory(category);
                logger.info("Category is deleted: {}", categoryName);
                return new JsonResponse(Status.SUCCESS);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            return new JsonResponse(Status.SERVER_ERROR);
        }
    }
}
