package com.myblog.myblog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Post {
    public static final long serialVersionUID = 1L;

    private Integer postId;

    private String title;

    private String content;

    private Integer categoryId;

    private LocalDate publishDate;

    private String excerpt;

    private Integer lastPostId;

    private Integer nextPostId;

}