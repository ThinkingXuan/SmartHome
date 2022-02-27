package com.nuist.you.smarthome.http.two;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.nuist.you.smarthome.MyApplication;
import com.orhanobut.logger.Logger;

/**
 * created by youxuan
 * on 2020/4/5
 */
public class NetStatusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {

            // 获取网络连接管理器
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) MyApplication.getInstance().getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取当前网络状态信息
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();

            if (info != null && info.isAvailable()) {
                Logger.t("WsManager").d("监听到可用网络切换,调用重连方法");
                WsManager.getInstance().reconnect();//wify 4g切换重连websocket
            }

        }
    }
}
