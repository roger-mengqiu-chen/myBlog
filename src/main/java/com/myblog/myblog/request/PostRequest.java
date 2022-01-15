package com.myblog.myblog.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PostRequest {
    @JsonProperty
    private Integer postId;
    @JsonProperty
    private String title;
    @JsonProperty
    private String content;
    @JsonProperty
    private String excerpt;
    @JsonProperty
    private String category;
    @JsonProperty
    private List<String> tags;
}
