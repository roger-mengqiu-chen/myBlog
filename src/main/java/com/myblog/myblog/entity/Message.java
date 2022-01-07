package com.myblog.myblog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message {

    private long messageId;

    private String pageName;

    private int pId;

    private int commenterId;

    private int replierId;

    private String messageDate;

    private String messageContent;

    public Message(String pageName, int commenterId, int replierId, String date, String content) {
        this.pageName = pageName;
        this.commenterId = commenterId;
        this.replierId = replierId;
        this.messageDate = date;
        this.messageContent = content;
    }
}
