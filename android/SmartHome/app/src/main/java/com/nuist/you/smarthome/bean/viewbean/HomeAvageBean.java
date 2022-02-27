package com.nuist.you.smarthome.bean.viewbean;

/**
 * created by youxuan
 * on 2020/4/11
 */
public class HomeAvageBean {
    private String AvageTitle;
    private String AvageData;

    public String getAvageTitle() {
        return AvageTitle;
    }

    public void setAvageTitle(String avageTitle) {
        AvageTitle = avageTitle;
    }

    public String getAvageData() {
        return AvageData;
    }

    public void setAvageData(String avageData) {
        AvageData = avageData;
    }

    @Override
    public String toString() {
        return "HomeAvageBean{" +
                "AvageTitle='" + AvageTitle + '\'' +
                ", AvageData='" + AvageData + '\'' +
                '}';
    }
}
