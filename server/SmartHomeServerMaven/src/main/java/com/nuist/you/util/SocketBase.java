package com.nuist.you.util;

/**
 * author: youxuan
 */

public class SocketBase {
    public static final int PORT_SERVER_ESP8266 = 8887;
    public static final int PORT_SERVER_ANDROID = 8888;

    //在用户登录时改变
    public static String userPhone = "";

    public static final String TO_ANDROID_STR = "发送给Android的消息：";
    public static final String TO_ESP_STR = "发送给ESP8266的消息：";
    public static final String FROM_ANDROID_STR = "来自Android的消息：";
    public static final String FROM_ESP_STR = "来自ESP8266的消息：";

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";


    public static final int CODE_SUCCESS = 1;
    public static final int CODE_FAILURE = 0;


    /*************客户端发送服务器的控制命令消息头***********/
    //控制命令
    public static final String MESSAGE_CONTROAL = "control";
    public static final int CODE_CONTROAL = 3;

    //请求小时，天等平均数据,用户APP主页的顶部显示
    public static final String MESSAGE_AVG_DATA = "avg_data";
    public static final int CODE_AVG_DATA = 4;

    //请求小时，天等平均数据，用户绘制图表
    public static final String MESSAGE_DRAW_CHART = "draw_chart";
    public static final int CODE_DRAW_CHART = 5;


    /******************************************************/


    //客户端发送服务器的控制命令消息头
    public static final String OPEN = "open";
    public static final String CLOSE = "close";

    //服务器发送ESP8266的指令
    public static final String BEEP_OPEN = "beep_open";
    public static final String BEEP_CLOSE = "beep_close";
    public static final String RELAY_OPEN = "relay_open";
    public static final String RELAY_CLOSE = "relay_close";
    public static final String DC_OPEN = "dc_open";
    public static final String DC_CLOSE = "dc_close";


    //设备类型
    public static final String BEEP_DEVICE = "beep";
    public static final String LIGHT_DEVICE = "light";  //同relay ，relay控制light
    public static final String FAN_DEVICE = "fan";


    //控制动作
    public static final String CONTROL_ACTION_OPEN = "open";
    public static final String CONTROL_ACTION_CLOSE = "close";

    //控制方式
    public static final String CONTROL_MODE_AUTO = "auto";
    public static final String CONTROL_MODE_MANUAL = "manual";

    //控制理由
    public static final String CONTROL_REASON_ROUTINE = "routine";
    public static final String CONTROL_REASON_ABNORMAL = "abnormal";

    /**
     * 来自ESP的数据格式:
     * 类型：传感器回传的数据  1 23 22 22 22
     * 控制命令的响应
     * 保留字段
     * 统一第一个数据为消息头，后面的为内容
     */
    public static final String FROM_ESP_SENSOR_CODE = "1";

    public static final String FROM_ESP_CONTROL_CODE = "2";


    //表名
    public static final String TABLE_AVERAGE_MIN = "average_min";
    public static final String TABLE_AVERAGE_HOUR = "average_hour";
    public static final String TABLE_AVERAGE_DAY = "average_day";
    public static final String TABLE_AVERAGE_WEEK = "average_week";
    public static final String TABLE_AVERAGE_MONTH = "average_month";
    public static final String TABLE_AVERAGE_YEAR = "average_year";

    //格式化时间的时间格式
    public static final String TIME_FORMAT_STR_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT_STR_MIN = "yyyy-MM-dd HH:mm";
    public static final String TIME_FORMAT_STR_HOUR = "yyyy-MM-dd HH";
    public static final String TIME_FORMAT_STR_DAY = "yyyy-MM-dd";
    public static final String TIME_FORMAT_STR_MONTH = "yyyy-MM";
    public static final String TIME_FORMAT_STR_YEAR = "yyyy";

    //查询数据库时的时间格式
    public static final String TIME_FORMAT_MIN = "%Y-%m-%d %H %i";
    public static final String TIME_FORMAT_HOUR = "%Y-%m-%d %H";
    public static final String TIME_FORMAT_DAY = "%Y-%m-%d";
    public static final String TIME_FORMAT_MONTH = "%Y-%m";
    public static final String TIME_FORMAT_YEAR = "%Y";


    public static final String TIME_STR_HOUR = "hour";
    public static final String TIME_STR_DAY = "day";
    public static final String TIME_STR_TODAY = "today";
    public static final String TIME_STR_YESTERDAY = "yesterday";
    public static final String TIME_STR_MONTH = "month";
    public static final String TIME_STR_YEAR = "year";


}
