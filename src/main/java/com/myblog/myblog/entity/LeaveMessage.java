package com.myblog.myblog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LeaveMessage {

    private long id;

    private String pageName;

    private int pId;

    private int commenterId;

    private int replierId;

    private String date;

    private String content;

    public LeaveMessage(String pageName, int commenterId, int replierId, String date, String content) {
        this.pageName = pageName;
        this.commenterId = commenterId;
        this.replierId = replierId;
        this.date = date;
        this.content = content;
    }
}
