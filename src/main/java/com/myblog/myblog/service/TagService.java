package com.myblog.myblog.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myblog.myblog.constant.Status;
import com.myblog.myblog.entity.Tag;
import com.myblog.myblog.mapper.TagMapper;
import com.myblog.myblog.response.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
    @Autowired
    private TagMapper tagMapper;

    private final Logger logger = LoggerFactory.getLogger(TagService.class);

    public JsonResponse createTags (List<String> tagNames) {
        List<String> allTags = tagMapper.getAllTagNames();
        List<String> existedTags = new ArrayList<>();
        List<String> tagsWithError = new ArrayList<>();
        List<String> createdTags = new ArrayList<>();
        for (String tag : tagNames) {
            tag = tag.toLowerCase().trim();
            if (allTags.contains(tag)) {
                existedTags.add(tag);
            }
            else {
                try {
                    tagMapper.createTag(tag);
                    createdTags.add(tag);
                } catch (Exception e) {
                    tagsWithError.add(tag);
                    logger.error(e.getMessage());
                }
            }
        }
        if (!existedTags.isEmpty()){
            logger.debug("Tried to create tags but these tags already existed: {}", existedTags.stream().map(Object::toString).collect(Collectors.joining(", ")));
        }
        if (!tagsWithError.isEmpty()) {
            logger.error("Tried to create tags but these tags have error: {}", tagsWithError.stream().map(Object::toString).collect(Collectors.joining(", ")));
            return new JsonResponse(Status.TAGS_PARTIALLY_CREATED);
        }
        else {
            return new JsonResponse(Status.SUCCESS, createdTags);
        }
    }

    public JsonResponse getAllTags() {
        List<Tag> tags = tagMapper.getAllTags();
        return new JsonResponse(Status.SERVER_ERROR);
    }

    public JsonResponse updateTag(Tag tag) {
        Integer tagId = tag.getTagId();
        if (tagId == null || tagMapper.findTagById(tagId) == null) {
            return new JsonResponse(Status.TAG_NOT_FOUND);
        }
        else {
            try {
                tagMapper.updateTag(tag);
                logger.info("Tag updated: {}", tagId);
                return new JsonResponse(Status.SUCCESS, tag);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            return new JsonResponse(Status.SERVER_ERROR);
        }
    }

    public JsonResponse deleteTag (String tagName) {
        Tag tag = tagMapper.findTagByName(tagName);
        if (tag == null) {
            logger.debug("Tried to delete a tag but tag does not exist: {}", tagName);
            return new JsonResponse(Status.TAG_NOT_FOUND);
        }
        else {
            try {
                tagMapper.deleteTag(tag);
                return new JsonResponse(Status.SUCCESS);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            return new JsonResponse(Status.SERVER_ERROR);
        }
    }
}
