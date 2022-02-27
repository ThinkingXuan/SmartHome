package com.nuist.you.smarthome.base;

/**
 * created by youxuan
 * on 2020/4/6
 */
public class BaseString {
    public static final String OPEN = "open";
    public static final String CLOSE = "close";

    //设备类型
    public static final String DEVICE_BEEP = "beep";
    public static final String DEVICE_FAN = "fan";
    public static final String DEVICE_LAMP = "lamp";  //同light


    /*************客户端发送服务器数据消息头***********/
    //客户端发送服务器的控制命令
    public static final String MESSAGE_CONTROAL = "control";
    public static final int CODE_CONTROAL = 3;

    //请求小时，天等平均数据
    public static final String MESSAGE_AVG_DATA = "avg_data";
    public static final int CODE_AVG_DATA = 4;

    //请求小时，天等平均数据，用户绘制图表
    public static final String MESSAGE_DRAW_CHART = "draw_chart";
    public static final int CODE_DRAW_CHART = 5;

    /******************************************************/

    public static final String TIME_STR_HOUR = "hour";
    public static final String TIME_STR_TODAY = "today";
    public static final String TIME_STR_DAY = "day";
    public static final String TIME_STR_YESTERDAY = "yesterday";
    public static final String TIME_STR_MONTH = "month";
    public static final String TIME_STR_YEAR = "year";

    public static final String DATA_TITLE_HOUR = "小时";
    public static final String DATA_TITLE_TODAY = "今天";
    public static final String DATA_TITLE_YESTERDAY = "昨天";
    public static final String DATA_TITLE_MONTH = "本月";
    public static final String DATA_TITLE_YEAR = "本年";

    public static final String DATA_TEMP = "temp";  //温度
    public static final String DATA_HUM = "hum";  //湿度
    public static final String DATA_MQ2 = "mq2"; //烟雾浓度
    public static final String DATA_LIGHT = "light"; //关照强度


}
