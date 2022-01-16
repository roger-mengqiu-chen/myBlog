package com.myblog.myblog.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateAdminRequest {
    @JsonProperty
    private String password;
    @JsonProperty
    private String email;
}
