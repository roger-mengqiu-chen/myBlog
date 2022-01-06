package com.myblog.myblog.entity;

import lombok.Data;

@Data
public class Comment {

    private long id;

    private long articleId;

    private long pId = 0;

    private int commenterId;

    private int replierId;
}