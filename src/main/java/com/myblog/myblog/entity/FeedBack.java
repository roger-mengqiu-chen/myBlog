package com.myblog.myblog.entity;

import lombok.Data;

@Data
public class FeedBack {
    private int id;

    private String content;

    private String contactInfo;

    private int personId;

    private String date;
}
