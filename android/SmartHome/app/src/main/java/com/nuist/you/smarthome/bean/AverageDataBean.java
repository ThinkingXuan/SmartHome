package com.nuist.you.smarthome.bean;

import java.io.Serializable;
import java.util.Date;

//
public class AverageDataBean implements Serializable {

    private String avg_id;
    private float avg_temp;
    private float avg_hum;
    private float avg_mq2;
    private float avg_beam;
    private Date avg_time;

    public String getAvg_id() {
        return avg_id;
    }

    public void setAvg_id(String avg_id) {
        this.avg_id = avg_id;
    }

    public float getAvg_temp() {
        return avg_temp;
    }

    public void setAvg_temp(float avg_temp) {
        this.avg_temp = avg_temp;
    }

    public float getAvg_hum() {
        return avg_hum;
    }

    public void setAvg_hum(float avg_hum) {
        this.avg_hum = avg_hum;
    }

    public float getAvg_mq2() {
        return avg_mq2;
    }

    public void setAvg_mq2(float avg_mq2) {
        this.avg_mq2 = avg_mq2;
    }

    public float getAvg_beam() {
        return avg_beam;
    }

    public void setAvg_beam(float avg_beam) {
        this.avg_beam = avg_beam;
    }

    public Date getAvg_time() {
        return avg_time;
    }

    public void setAvg_time(Date avg_time) {
        this.avg_time = avg_time;
    }

    @Override
    public String toString() {
        return "AverageDataBean{" +
                "avg_id='" + avg_id + '\'' +
                ", avg_temp=" + avg_temp +
                ", avg_hum=" + avg_hum +
                ", avg_mq2=" + avg_mq2 +
                ", avg_beam=" + avg_beam +
                ", avg_time=" + avg_time +
                '}';
    }
}
