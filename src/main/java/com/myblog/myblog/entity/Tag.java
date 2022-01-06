package com.myblog.myblog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Tag {
    private int id;

    private String name;

    public Tag(String name) {
        this.name = name;
    }
}