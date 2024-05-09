package com.zhonghai.drawingapp.entity;

import java.security.Principal;

public class StompPrincipal implements Principal {
    private final String name;  // 在这个上下文中，name将用来存储boardId

    public StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;  // 返回boardId
    }
}