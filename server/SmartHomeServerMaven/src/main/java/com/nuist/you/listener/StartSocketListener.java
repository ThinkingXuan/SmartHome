package com.nuist.you.listener;

import com.nuist.you.espsocket.ServerListener;
import com.nuist.you.mywebsocket.WebSocketListener;
import com.nuist.you.statistics.DataStatisticsListener;
import com.nuist.you.util.LogUtil;
import com.nuist.you.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * author: youxuan
 */
/**
 * 用户开启WebSocket和Socket,程序的入口
 */
public class StartSocketListener implements ServletContextListener {

    ServerListener serverListener;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        startDataStatistics();
        startWebSocket();
        startSocket();

//        sce.getServletContext().
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        LogUtil.info("contextDestroyed");
    }

    //开启WebSocket

    void startWebSocket() {
        WebSocketListener webSocketListener = new WebSocketListener();
        webSocketListener.run();

    }

    //开启Socket();
    void startSocket() {
        Timer timer = new Timer();
        timer.start();

        //开启延时，不然Tomcat启动不成功
        timer.schedule(3000, () -> {
            if (null == serverListener) {
                ServerListener serverListener = new ServerListener();
                serverListener.run();
            }
        });

    }

    //开启定时器统计数据
    void startDataStatistics() {
        DataStatisticsListener dataStatisticsListener = new DataStatisticsListener();
        dataStatisticsListener.run();
    }

}
