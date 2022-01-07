package com.myblog.myblog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Role {

    private int roleId;

    private String roleName;

    public Role(int id, String name) {
        this.roleId = id;
        this.roleName = name;
    }
}