package com.nuist.you.smarthome.bean;

import java.util.List;

/**
 * ESP8266上传的服务器数据
 * {"message": "","code": 1,"object": {"user_id": "","time": "","temperature": "","humidity": "","MQ_2": "","light": "","infrared": ""}}
 */
public class SensorData {


    /**
     * message :
     * code : 1
     * object : {"user_id":"","time":"","temperature":"","humidity":"","MQ_2":"","light":"","infrared":""}
     */

    private String message;
    private int code;
    private SensorDataBean object;

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

    public SensorDataBean getObject() {
        return object;
    }

    public void setObject(SensorDataBean object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", object=" + object +
                '}';
    }
}
