package com.myblog.myblog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Post {
    public static final long serialVersionUID = 1L;

    private int postId;

    private String title;

    private String content;

    private int category;

    private LocalDate publishDate;

    private String excerpt;

    private long lastPostId;

    private long nextPostId;

}