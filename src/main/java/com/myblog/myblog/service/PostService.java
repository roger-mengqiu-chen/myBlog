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
import java.util.Arrays;
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

        Integer categoryId = createCategoryIfNotExist(category);
        post.setCategoryId(categoryId);

        // check the existence of each tag, if it doesn't exist, create a new one. And get ids of all the tags.
        List<Integer> tagIds = createTagsIfNotExist(tags);

        // get id of last post
        Post lastPost = postMapper.getLatestPost();
        Integer lastPostId = null;
        if (lastPost != null) {
            lastPostId = lastPost.getPostId();
        }

        // save post
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

        // update relationship between last post and current post
        Post savedPost = postMapper.getPostByTitle(title);
        if (lastPost != null) {
            lastPost.setNextPostId(savedPost.getPostId());
            postMapper.updatePost(lastPost);
        }

        updateTagsOfPost(savedPost.getPostId(), tagIds);

        createArchiveForPost(savedPost);

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

    public JsonResponse findAllPostOfCategory(String category, int pageNo){
        try {
            List<Post> posts = postMapper.getPostByCategory(category, (pageNo - 1) * 10);
            return new JsonResponse(Status.SUCCESS, posts);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new JsonResponse(Status.SERVER_ERROR);
        }
    }

    public JsonResponse findAllPostOfTag(String tag, int pageNo){
        try {
            List<Post> posts = postMapper.getPostByTagName(tag, (pageNo - 1) * 10);
            return new JsonResponse(Status.SUCCESS, posts);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new JsonResponse(Status.SERVER_ERROR);
        }
    }

    public JsonResponse getPostById(int postId){
        try {
            Post post = postMapper.getPostById(postId);
            return new JsonResponse(Status.SUCCESS, post);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new JsonResponse(Status.SERVER_ERROR);
        }
    }

    public JsonResponse updatePost(int postId, String title, String content, String excerpt, String category, List<String> tags) {
        Post post = postMapper.getPostById(postId);
        if (post == null) {
            return new JsonResponse(Status.POST_NOT_FOUND);
        }
        Post existedPost = postMapper.getPostByTitle(title);
        if (existedPost.getPostId() != postId) {
            return new JsonResponse(Status.POST_TITLE_EXISTED);
        }

        int categoryId = createCategoryIfNotExist(category);
        List<Integer> tagIds = createTagsIfNotExist(tags);
        post.setTitle(title);
        post.setContent(content);
        post.setExcerpt(excerpt);
        post.setCategoryId(categoryId);

        updateTagsOfPost(postId, tagIds);
        postMapper.updatePost(post);
        return new JsonResponse(Status.SUCCESS, post);
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

    /**
     * Create a new archive for a post
     * Archvive naming format is yyyy-mm
     * @param post post to be updated
     */
    private void createArchiveForPost(Post post) {
        Archive archive = new Archive();
        String name = post.getPublishDate().toString().substring(0,7);
        int postId = post.getPostId();
        archive.setPostId(postId);
        archive.setArchiveName(name);
        try {
            archiveMapper.save(archive);
            logger.info("Created a new archive: {}", name);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Create a category if doesn't exist
     * @param category category to be created
     * @return the id of existed category or created category
     */
    private Integer createCategoryIfNotExist(String category) {
        category = category.trim().toLowerCase();
        Category existedCategory = categoryMapper.findCategoryByName(category);
        if (existedCategory != null) {
            return existedCategory.getCategoryId();
        }
        else {
            categoryMapper.createCategory(category);
            existedCategory = categoryMapper.findCategoryByName(category);
            return existedCategory.getCategoryId();
        }
    }

    /**
     * Create tags if they don't exist.
     * @param tags tags to be created
     * @return ids of existed tag or created tags
     */
    private List<Integer> createTagsIfNotExist(List<String> tags) {
        List<Integer> tagIds = new ArrayList<>();
        String[] createdTags = new String[tags.size()];
        for (int i = 0; i < tags.size(); i ++) {
            String tag = tags.get(i);
            Tag existedTag = tagMapper.findTagByName(tag);
            if (existedTag == null) {
                tagMapper.createTag(tag);
                Integer id = tagMapper.findTagByName(tag).getTagId();
                tagIds.add(id);
                createdTags[i] = tag;
            }
            else {
                tagIds.add(existedTag.getTagId());
            }
        }
        if (createdTags.length != 0) {
            Object[] objects = Arrays.stream(createdTags).filter(e -> !e.isEmpty()).toArray();
            logger.info("Created tags: {}", Arrays.toString(objects));
        }
        return tagIds;
    }

    /**
     * Update tags of a post
     * The procedure includes delete all the tags of a post
     * And attach new tags to the post
     * This method can also be used for a new post
     * @param postId post to be updated
     * @param tagIds new tags to be attached
     */
    private void updateTagsOfPost(int postId, List<Integer> tagIds) {
        tagMapper.deleteTagOfPost(postId);
        for (int tagId : tagIds) {
            tagMapper.insertPostTag(postId, tagId);
        }
        logger.info("Tags of post {} has been updated", postId);
    }
}
