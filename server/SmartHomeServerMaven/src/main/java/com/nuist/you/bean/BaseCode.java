package com.nuist.you.bean;

public class BaseCode {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
