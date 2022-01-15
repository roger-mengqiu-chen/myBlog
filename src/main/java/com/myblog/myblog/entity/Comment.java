package com.myblog.myblog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class Comment {

    private Long commentId;

    private Long postId;

    private Long pId;

    private Integer commenterId;

    private Timestamp commentTime;

    private String commentContent;
}