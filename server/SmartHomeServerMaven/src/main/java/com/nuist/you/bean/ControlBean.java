package com.nuist.you.bean;

/**
 * created by youxuan
 * on 2020/4/5
 */

//客户端发送服务器的控制指令Bean
public class ControlBean {

    private String message;
    private int code;
    private ControlDeviceBean object;

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

    public ControlDeviceBean getObject() {
        return object;
    }

    public void setObject(ControlDeviceBean object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "ControlBean{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", object=" + object +
                '}';
    }
}
