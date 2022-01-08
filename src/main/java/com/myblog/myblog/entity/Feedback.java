package com.myblog.myblog.entity;

import lombok.Data;

@Data
public class Feedback {
    private int feedbackId;

    private String feedbackContent;

    private String email;

    private String feedbackDate;
}
