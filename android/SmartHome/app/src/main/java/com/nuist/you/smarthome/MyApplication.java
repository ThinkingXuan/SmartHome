package com.nuist.you.smarthome;

import android.app.Application;

import com.mob.MobSDK;
import com.nuist.you.smarthome.http.two.ForegroundCallbacks;
import com.nuist.you.smarthome.http.two.WsManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class MyApplication extends Application {

    private static MyApplication wsApplication;

    public static MyApplication getInstance() {
        return wsApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        wsApplication = this;
        Logger.addLogAdapter(new AndroidLogAdapter());
        initAppStatusListener();
        initMobSDK();
    }

    private void initMobSDK() {
        //初始化mob短信服务
        MobSDK.init(this);
    }

    private void initAppStatusListener() {
        ForegroundCallbacks.init(this).addListener(new ForegroundCallbacks.Listener() {
            @Override
            public void onBecameForeground() {
                Logger.t("WsManager").d("应用回到前台调用重连方法");
                WsManager.getInstance().reconnect();
            }

            @Override
            public void onBecameBackground() {

            }
        });
    }

}
