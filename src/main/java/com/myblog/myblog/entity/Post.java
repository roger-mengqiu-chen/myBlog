package com.myblog.myblog.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Post {
    public static final long serialVersionUID = 1L;

    private int postId;

    private String title;

    private String content;

    private int category;

    private Date publishDate;

    private String excerpt;

    private long lastPostId;

    private long nextPostId;

}