package com.nuist.you.bean;

/**
 * created by youxuan
 * on 2020/4/5
 */
public class ControlDeviceBean {
    private String user_id;
    private String time;
    private String beep;
    private String relay;
    private String dc_motor;
    private int dc_motor_pwm;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBeep() {
        return beep;
    }

    public void setBeep(String beep) {
        this.beep = beep;
    }

    public String getRelay() {
        return relay;
    }

    public void setRelay(String relay) {
        this.relay = relay;
    }

    public String getDc_motor() {
        return dc_motor;
    }

    public void setDc_motor(String dc_motor) {
        this.dc_motor = dc_motor;
    }

    public int getDc_motor_pwm() {
        return dc_motor_pwm;
    }

    public void setDc_motor_pwm(int dc_motor_pwm) {
        this.dc_motor_pwm = dc_motor_pwm;
    }

    @Override
    public String toString() {
        return "ControlDeviceBean{" +
                "user_id='" + user_id + '\'' +
                ", time='" + time + '\'' +
                ", beep='" + beep + '\'' +
                ", relay='" + relay + '\'' +
                ", dc_motor='" + dc_motor + '\'' +
                ", dc_motor_pwm=" + dc_motor_pwm +
                '}';
    }
}
