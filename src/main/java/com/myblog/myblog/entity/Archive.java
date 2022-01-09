package com.myblog.myblog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Archive {

    private int archiveId;

    private String archiveName;

    private int postId;
}
