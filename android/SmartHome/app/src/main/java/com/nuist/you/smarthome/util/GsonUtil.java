package com.nuist.you.smarthome.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * author: youxuan
 */

public class GsonUtil {

    public static <T> String getJsonStr(T t) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String str = gson.toJson(t);
        if (str.length() > 0) {
            return str;
        } else {
            return "";
        }
    }

    public static <T> T getObject(String json, Class<T> cls) {
//        Gson gson = new Gson();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        T t = gson.fromJson(json, cls);
        return t;
    }

//    public static <T> List<T> getObject(Class<T> cls, String json) {
//        List<T> list = new ArrayList<>();
//        Gson gson = new Gson();
//        list = gson.fromJson(json, new TypeToken<List<T>>() {
//        }
//                .getType());
//
//        if (list.size() > 0 && null != list) {
//            return list;
//        }
//        return null;
//    }

//    public static SensorData ConvertSensor(String message, int code, String content) {
//        SensorData sensorData = new SensorData();
//        sensorData.setMessage(message);
//        sensorData.setCode(code);
//
//        List<SensorDataBean> list = new ArrayList<>();
//        SensorDataBean sensorBean = new SensorDataBean();
//
//        String[] item = content.split(" ");
//        sensorBean.setTemperature(item[0]);
//        sensorBean.setHumidity(item[1]);
//        sensorBean.setMQ_2(item[2]);
//        sensorBean.setLight(item[3]);
//        sensorBean.setInfrared(item[4]);
//        list.add(sensorBean);
//
//        sensorData.setObject(list);
//        return sensorData;
//
//    }

}
