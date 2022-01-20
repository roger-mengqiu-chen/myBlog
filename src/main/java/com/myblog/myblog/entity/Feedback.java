package com.myblog.myblog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Feedback {
    private Integer feedbackId;

    private String feedbackContent;

    private String email;

    private LocalDate feedbackDate;
}
