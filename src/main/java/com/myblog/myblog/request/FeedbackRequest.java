package com.myblog.myblog.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FeedbackRequest {
    @JsonProperty
    private String email;
    @JsonProperty
    private String content;
}
