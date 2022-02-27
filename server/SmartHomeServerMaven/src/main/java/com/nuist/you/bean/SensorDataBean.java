package com.nuist.you.bean;

import java.util.Date;

public class SensorDataBean {
    /**
     * id :
     * time :
     * temperature :
     * humidity :
     * MQ_2 :
     * light :
     */
    private String id;
    private Date time;
    private int temperature;
    private int humidity;
    private int mq2;
    private int beam;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getMq2() {
        return mq2;
    }

    public void setMq2(int mq2) {
        this.mq2 = mq2;
    }

    public int getBeam() {
        return beam;
    }

    public void setBeam(int beam) {
        this.beam = beam;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id='" + id + '\'' +
                ", time=" + time +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", mq2=" + mq2 +
                ", beam=" + beam +
                '}';
    }

}
