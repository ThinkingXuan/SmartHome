package com.nuist.you.smarthome.listener;

import android.view.View;

/**
 * author: youxuan
 */

//用于接收Adnroid端和ESP8266的数据
public interface ItemClickListener {
    void OnItemOnClickListener(View v,int position);
}
