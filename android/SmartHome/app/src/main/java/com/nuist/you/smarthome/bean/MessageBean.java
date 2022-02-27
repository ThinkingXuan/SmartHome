package com.nuist.you.smarthome.bean;

/**
 *  {message : "ok", code ：1}
 */

public class MessageBean {
    private String message;
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
