package com.myblog.myblog.entity;

import lombok.Data;

@Data
public class Comment {

    private long commentId;

    private long postId;

    private long pId = 0;

    private int commenterId;

    private String commentDate;

    private String commentContent;
}