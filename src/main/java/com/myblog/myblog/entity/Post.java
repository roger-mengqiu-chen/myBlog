package com.myblog.myblog.entity;

import lombok.Data;

@Data
public class Post {
    public static final long serialVersionUID = 1L;

    private int id;

    private long postId;

    private String title;

    private String content;

    private String tags;

    private String type;

    private String category;

    private String publishDate;

    private String updateDate;

    private String excerpt;

    private long lastPostId;

    private long nextPostId;

}