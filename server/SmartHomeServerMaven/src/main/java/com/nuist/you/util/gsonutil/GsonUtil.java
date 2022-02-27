package com.nuist.you.util.gsonutil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nuist.you.bean.ControlBean;
import com.nuist.you.bean.MessageBean;
import com.nuist.you.bean.SensorData;
import com.nuist.you.bean.SensorDataBean;
import com.nuist.you.util.SocketBase;

/**
 * author: youxuan
 */

public class GsonUtil {

    //Objcet -> json
    public static <T> String getJsonStr(T t) {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd HH:mm:ss")
//                .create();
//        Gson gson = new Gson();
        String str = gson.toJson(t);
        if (str.length() > 0) {
            return str;
        } else {
            return "";
        }
    }

    public static MessageBean getMessageBean(String json) {

        Gson gson = new Gson();
        MessageBean messageBean = gson.fromJson(json, new TypeToken<MessageBean>() {
        }
                .getType());

        if (null != messageBean) {
            return messageBean;
        }

        return null;

    }

    //json -> SensorData
    public static SensorData getSensorData(String json) {

        Gson gson = new Gson();
        SensorData sensorData = gson.fromJson(json, new TypeToken<SensorData>() {
        }
                .getType());

        if (null != sensorData) {
            return sensorData;
        }

        return null;
    }

    //json -> ControlBean
    public static ControlBean getControlBean(String json) {

        Gson gson = new Gson();
        ControlBean controlBean = gson.fromJson(json, new TypeToken<ControlBean>() {
        }
                .getType());

        if (null != controlBean) {
            return controlBean;
        }

        return null;
    }


    //string->sensorData
    public static SensorData ConvertSensor(String message, int code, String content) {
        SensorData sensorData = new SensorData();
        sensorData.setMessage(message);
        sensorData.setCode(code);

        SensorDataBean sensorBean = new SensorDataBean();
        boolean isFlag = false;              //是否有乱码

        String[] item = content.split(" ");

            if (item.length < 4) {
                new RuntimeException("运行异常");
                return null;
            }


            //"".
            if (item[0].matches("[0-9]+")) {
                sensorBean.setTemperature(Integer.valueOf(item[0]));
            } else {
//            sensorBean.setTemperature(-100);
                isFlag = true;

            }

            if (item[1].matches("[0-9]+")) {
                sensorBean.setHumidity(Integer.valueOf(item[1]));
            } else {
//            sensorBean.setHumidity(-100);
                isFlag = true;
            }

            if (item[2].matches("[0-9]+")) {

                sensorBean.setMq2(Integer.valueOf(item[2]));
            } else {
//            sensorBean.setMq2(-100);
                isFlag = true;
            }

            if (item[3].matches("[0-9]+")) {
                sensorBean.setBeam(Integer.valueOf(item[3]));
            } else {
//            sensorBean.setBeam(-100);
                isFlag = true;
            }

            //如果有乱码
            if (isFlag) {
                sensorData.setMessage(SocketBase.FAILURE);
                sensorData.setCode(SocketBase.CODE_FAILURE);
            }

            sensorData.setObject(sensorBean);
            return sensorData;

        }

    }
