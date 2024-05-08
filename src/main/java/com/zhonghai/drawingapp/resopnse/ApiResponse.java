package com.zhonghai.drawingapp.resopnse;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private Integer code;
    private Boolean success;
    private String message;
    private T data;  // 使用泛型类型T来替代具体的数据类型

    public ApiResponse(Integer code, Boolean success, String message, T data) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }

}
