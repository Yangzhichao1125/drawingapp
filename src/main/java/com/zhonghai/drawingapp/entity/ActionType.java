package com.zhonghai.drawingapp.entity;

public enum ActionType {
    DRAW(1, "绘画"),
    UNDO(2, "撤销"),
    REDO(3, "重做");

    private final Integer code;
    private final String description;

    ActionType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}

