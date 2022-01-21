package com.myblog.myblog.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ModifyUserRequest {

    @JsonProperty
    private String username;
    @JsonProperty
    private String password;
    @JsonProperty
    private String email;

}
