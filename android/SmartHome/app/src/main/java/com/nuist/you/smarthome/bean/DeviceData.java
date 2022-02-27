package com.nuist.you.smarthome.bean;

import java.util.Date;

//灯光，蜂鸣器，风扇
public class DeviceData {

    private String id;
    private String userId;
    private String controlTime;
    private String deviceType;
    private String controlAction;
    private String controlMode;
    private String controlReason;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getControlTime() {
        return controlTime;
    }

    public void setControlTime(String controlTime) {
        this.controlTime = controlTime;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getControlAction() {
        return controlAction;
    }

    public void setControlAction(String controlAction) {
        this.controlAction = controlAction;
    }

    public String getControlMode() {
        return controlMode;
    }

    public void setControlMode(String controlMode) {
        this.controlMode = controlMode;
    }

    public String getControlReason() {
        return controlReason;
    }

    public void setControlReason(String controlReason) {
        this.controlReason = controlReason;
    }

    @Override
    public String toString() {
        return "DeviceData{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", controlTime=" + controlTime +
                ", deviceType='" + deviceType + '\'' +
                ", controlAction='" + controlAction + '\'' +
                ", controlMode='" + controlMode + '\'' +
                ", controlReason='" + controlReason + '\'' +
                '}';
    }
}
