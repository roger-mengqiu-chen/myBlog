package com.myblog.myblog.mapper;

import com.myblog.myblog.entity.Category;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoryMapper {
    /* Create */
    @Insert("INSERT INTO categories (categoryName) " +
            "VALUES (#{name})")
    int createCategory(String name);

    /* Read */
    @Select("SELECT categoryName FROM categories")
    List<String> getAllCategories();

    @Select("SELECT * FROM categories " +
            "WHERE categoryName = #{name}")
    @Results({
            @Result(id = true, property = "categoryId", column = "categoryId"),
            @Result(property = "categoryName", column = "categoryName")
    })
    Category findCategoryByName(String name);

    @Select("SELECT * FORM categories " +
            "WHERE categoryId = #{categoryId}")
    Category findCategoryById(Integer categoryId);

    /* Update */
    @Update("UPDATE categories " +
            "SET categoryName = #{categoryName}" +
            "WHERE categoryId = #{categoryId}")
    int updateCategory(Category category);

    /* Delete */
    @Delete("DELETE FROM categories " +
            "WHERE categoryId = #{categoryId}")
    int deleteCategory(Category category);

    @Delete("DELETE FROM cateogries " +
            "WHERE categoryId = #{categoryId}")
    int deleteCateogryById(int categoryId);
}
