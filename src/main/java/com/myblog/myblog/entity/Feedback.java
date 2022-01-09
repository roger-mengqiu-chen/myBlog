package com.myblog.myblog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Feedback {
    private int feedbackId;

    private String feedbackContent;

    private String email;

    private String feedbackDate;
}
