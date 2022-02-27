package com.nuist.you.smarthome.bean;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * created by youxuan
 * on 2020/4/5
 */
public class SensorDataBean {
    /**
     * user_id :
     * time :
     * temperature :
     * humidity :
     * MQ_2 :
     * light :
     * infrared :
     */

    private String id;
    private String time;
    private String temperature;
    private String humidity;
    private String mq2;
    private String beam;
    private String infrared;

    public String getUser_id() {
        return id;
    }

    public void setUser_id(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }


    public String getMq2() {
        return mq2;
    }

    public void setMq2(String mq2) {
        this.mq2 = mq2;
    }

    public String getBeam() {
        return beam;
    }

    public void setBeam(String beam) {
        this.beam = beam;
    }

    public String getInfrared() {
        return infrared;
    }

    public void setInfrared(String infrared) {
        this.infrared = infrared;
    }

    @Override
    public String toString() {
        return "SensorBean{" +
                "user_id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", mq2='" + mq2 + '\'' +
                ", beam='" + beam + '\'' +
                ", infrared='" + infrared + '\'' +
                '}';
    }
}
