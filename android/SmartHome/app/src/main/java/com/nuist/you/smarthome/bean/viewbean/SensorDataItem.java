package com.nuist.you.smarthome.bean.viewbean;

/**
 * created by youxuan
 * on 2020/4/9
 */

//主页viewpager的bean
public class SensorDataItem {
    private Integer topDrawable;
    private String dataType;
    private String data;
    private String tips;

    public Integer getTopDrawable() {
        return topDrawable;
    }

    public void setTopDrawable(Integer topDrawable) {
        this.topDrawable = topDrawable;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
