package com.myblog.myblog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Comment {

    private Long commentId;

    private Integer postId;

    private Long pId;

    private Integer commenterId;

    private LocalDateTime commentTime;

    private String commentContent;
}