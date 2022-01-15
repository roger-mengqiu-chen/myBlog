package com.myblog.myblog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Tag {
    private Integer tagId;

    private String tagName;

    public Tag(String name) {
        this.tagName = name;
    }
}