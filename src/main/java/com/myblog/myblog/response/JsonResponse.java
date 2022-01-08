package com.myblog.myblog.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myblog.myblog.constant.Status;

public class JsonResponse {

    private Status status;

    private Object data;

    public JsonResponse() {
    }

    public JsonResponse(Status status) {
        this.status = status;
    }

    public JsonResponse(Status status, Object data) {
        this.status = status;
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toJson() throws JsonProcessingException {

        return new ObjectMapper().writeValueAsString(this);

    }
}
