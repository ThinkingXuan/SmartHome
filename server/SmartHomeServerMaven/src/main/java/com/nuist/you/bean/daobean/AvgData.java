package com.nuist.you.bean.daobean;


public class AvgData {
    private String message;
    private int code;
    private AverageDataBean object;


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

    public AverageDataBean getObject() {
        return object;
    }

    public void setObject(AverageDataBean object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "AvgData{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", object=" + object +
                '}';
    }
}
