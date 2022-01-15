package com.myblog.myblog.service;

import com.myblog.myblog.constant.Status;
import com.myblog.myblog.entity.Category;
import com.myblog.myblog.entity.Post;
import com.myblog.myblog.entity.Tag;
import com.myblog.myblog.mapper.CategoryMapper;
import com.myblog.myblog.mapper.PostMapper;
import com.myblog.myblog.mapper.TagMapper;
import com.myblog.myblog.response.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;

    private final Logger logger = LoggerFactory.getLogger(PostService.class);

    public JsonResponse makePost(String title, String content, String excerpt, String category, LocalDate publishDate, List<String> tags) {
        Post post = new Post();

        category = category.trim().toLowerCase();
        // find existedCategory, if doesn't exist, create a new one
        Category existedCategory = categoryMapper.findCategoryByName(category);
        if (existedCategory != null) {
            post.setCategoryId(existedCategory.getCategoryId());
        }
        else {
            categoryMapper.createCategory(category);
            existedCategory = categoryMapper.findCategoryByName(category);
            post.setCategoryId(existedCategory.getCategoryId());
        }

        // check the existence of each tag, if it doesn't exist, create a new one. And get ids of all the tags.
        List<Integer> tagIds = new ArrayList<>();
        for (String tag : tags) {
            Tag existedTag = tagMapper.findTagByName(tag);
            if (existedTag == null) {
                tagMapper.createTag(tag);
                Integer id = tagMapper.findTagByName(tag).getTagId();
                tagIds.add(id);
            }
            else {
                tagIds.add(existedTag.getTagId());
            }
        }
        Post lastPost = postMapper.getLatestPost();
        Integer lastPostId = lastPost.getPostId();

        post.setTitle(title);
        post.setContent(content);
        post.setPublishDate(publishDate);
        post.setExcerpt(excerpt);
        post.setLastPostId(lastPostId);

        try {
            postMapper.savePost(post);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new JsonResponse(Status.SERVER_ERROR);
        }
        Integer postId = postMapper.getIdByPostTitle(title);

        lastPost.setNextPostId(postId);
        postMapper.updatePost(lastPost);

        for (Integer id : tagIds) {
            tagMapper.insertPostTag(postId, id);
        }

        return new JsonResponse(Status.SUCCESS, post);
    }

    public JsonResponse findAllPostOnPage(int pageNo) {
        try {
            List<Post> posts = postMapper.getAllPosts((pageNo-1) * 10);
            return new JsonResponse(Status.SUCCESS, posts);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new JsonResponse(Status.SERVER_ERROR);
        }
    }

}
