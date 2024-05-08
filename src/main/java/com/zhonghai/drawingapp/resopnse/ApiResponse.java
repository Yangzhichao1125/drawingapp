package com.zhonghai.drawingapp.resopnse;

import lombok.Data;

@Data
public class ApiResponse {
    private boolean success;
    private String message;
    private String data; // 这里用String类型存储nickname，根据需要可以扩展或修改

    public ApiResponse(boolean success, String message, String data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

}
