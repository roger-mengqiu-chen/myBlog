package com.myblog.myblog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Archive {

    private Integer archiveId;

    private String archiveName;

    private Integer postId;
}
