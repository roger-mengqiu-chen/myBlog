package com.myblog.myblog.service;

import com.myblog.myblog.constant.Status;
import com.myblog.myblog.entity.Archive;
import com.myblog.myblog.entity.Category;
import com.myblog.myblog.entity.Post;
import com.myblog.myblog.entity.Tag;
import com.myblog.myblog.mapper.ArchiveMapper;
import com.myblog.myblog.mapper.CategoryMapper;
import com.myblog.myblog.mapper.PostMapper;
import com.myblog.myblog.mapper.TagMapper;
import com.myblog.myblog.response.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private ArchiveMapper archiveMapper;

    private final Logger logger = LoggerFactory.getLogger(PostService.class);

    public JsonResponse makePost(String title, String content, String excerpt, String category, List<String> tags) {
        Post post = new Post();

        if (postMapper.getPostByTitle(title) != null) {
            return new JsonResponse(Status.POST_TITLE_EXISTED);
        }

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
        Integer lastPostId = null;
        if (lastPost != null) {
            lastPostId = lastPost.getPostId();
        }
        LocalDate date = LocalDate.now();
        post.setTitle(title);
        post.setContent(content);
        post.setPublishDate(date);
        post.setExcerpt(excerpt);
        post.setLastPostId(lastPostId);

        try {
            postMapper.savePost(post);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new JsonResponse(Status.SERVER_ERROR);
        }
        // update relationship between laste post and current post
        Post savedPost = postMapper.getPostByTitle(title);
        if (lastPost != null) {
            lastPost.setNextPostId(savedPost.getPostId());
            postMapper.updatePost(lastPost);
        }

        for (Integer id : tagIds) {
            tagMapper.insertPostTag(savedPost.getPostId(), id);
        }

        // persist archive
        // archive naming format should be yyyy-mm
        Archive archive = new Archive();
        archive.setArchiveName(date.toString().substring(0,7));
        archive.setPostId(savedPost.getPostId());
        archiveMapper.save(archive);
        logger.info("Created a new post");
        return new JsonResponse(Status.SUCCESS, savedPost);
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

    public JsonResponse deletePost(int postId) {
        Post post = postMapper.getPostById(postId);
        if (post == null) {
            logger.error("Can't find this post");
            return new JsonResponse(Status.POST_NOT_FOUND);
        }
        else {
            // get previous post
            Post previousPost = postMapper.getPostById(post.getLastPostId());
            // get next post
            Post nextPost = postMapper.getPostById(post.getNextPostId());
            // connect previous and next post
            if (previousPost != null && nextPost == null) {
                previousPost.setNextPostId(null);
            }
            else if (previousPost == null && nextPost != null) {
                nextPost.setLastPostId(null);
            }
            else if (previousPost != null && nextPost != null) {
                previousPost.setNextPostId(nextPost.getPostId());
                nextPost.setLastPostId(previousPost.getPostId());
            }
            postMapper.updatePost(previousPost);
            postMapper.updatePost(nextPost);
            // delete post
            postMapper.deletePost(post);
            // delete post_tags
            tagMapper.deleteTagOfPost(postId);
            // delete category if no post exist under this category
            int categoryId = post.getCategoryId();

            // delete archive
            String archiveName = post.getPublishDate().toString().substring(0,7);
            archiveMapper.deleteArchiveByPostId(postId);
            logger.info("Post is deleted");
            return new JsonResponse(Status.SUCCESS);
        }
    }
}
