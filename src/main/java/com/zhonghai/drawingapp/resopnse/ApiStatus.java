package com.zhonghai.drawingapp.resopnse;

public enum ApiStatus {
    SUCCESS(true, 200, "操作成功"),
    BOARD_NOT_FOUND(false, 404, "画板不存在或无法加入");

    private final boolean success;
    private final int statusCode;
    private final String message;

    ApiStatus(boolean success, int statusCode, String message) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}

