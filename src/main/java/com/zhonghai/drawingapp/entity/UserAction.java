package com.zhonghai.drawingapp.entity;

import lombok.Data;

@Data
public class UserAction {

    //类型 1 绘画， 2 撤销 3 重做
    private Integer type;
    private String message;
    private String drawingId;
    private String nickName;
}
