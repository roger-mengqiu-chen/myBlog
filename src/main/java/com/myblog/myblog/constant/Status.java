package com.myblog.myblog.constant;

public enum Status {

    SUCCESS(0, "Success"),

    USER_NOT_FOUND(100, "Username not found"),
    USER_NAME_EXISTED(101, "Username existed"),
    USER_EMAIL_EXISTED(102, "Email existed"),

    POST_NOT_FOUND(200, "Post not found"),
    POST_TITLE_EXISTED(201, "Post title existed"),

    ARCHIVE_NOT_FOUND(300, "Archive not found"),

    PERMISSION_DENIED(403, "Permission denied"),

    SERVER_ERROR(500, "Server internal error"),

    TAG_NOT_FOUND(600, "Tag not found"),
    TAG_EXIST(601, "Tag existed"),
    TAGS_PARTIALLY_CREATED(602, "Tags are partially created"),

    CATEGORY_NOT_FOUND(700, "Category not found"),
    CATEGORY_EXIST(701, "Category exists"),

    FILE_EMPTY(800, "File is empty"),

    COMMENT_NOT_FOUND(900, "Comment not found"),
    INVALID_FORMAT(999, "Input format is invalid"),
    ;

    private final int code;
    private final String message;

    Status(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
