package com.myblog.myblog.entity;

import lombok.Data;

@Data
public class Post {
    public static final long serialVersionUID = 1L;

    private int postId;

    private String title;

    private String content;

    private int category;

    private String publishDate;

    private String excerpt;

    private long lastPostId;

    private long nextPostId;

}