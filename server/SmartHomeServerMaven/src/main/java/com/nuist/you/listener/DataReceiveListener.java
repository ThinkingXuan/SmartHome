package com.nuist.you.listener;

/**
 * author: youxuan
 */

//用于接收Adnroid端和ESP8266的数据
public interface DataReceiveListener {
    void onReceiveAndroid(String data);
    void onReceiveESP8266(String data);
}
