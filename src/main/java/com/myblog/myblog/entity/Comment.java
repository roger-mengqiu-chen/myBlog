package com.myblog.myblog.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {

    private long commentId;

    private long postId;

    private long pId = 0;

    private int commenterId;

    private Timestamp commentTime;

    private String commentContent;
}