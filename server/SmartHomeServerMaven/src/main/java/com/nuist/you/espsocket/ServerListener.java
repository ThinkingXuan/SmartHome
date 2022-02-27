package com.nuist.you.espsocket;

import com.nuist.you.datamanager.DataDealManager;
import com.nuist.you.util.LogUtil;
import com.nuist.you.util.SocketBase;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * author: youxuan
 */

public class ServerListener extends Thread {
    @Override
    public void run() {
        try {
            System.out.println("ESP8266服务器已开启：");

            // 1、创建ServerScoket，设置端口
            int port = SocketBase.PORT_SERVER_ESP8266;
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                // 2、accept方法将导致程序阻塞
                Socket socket = serverSocket.accept();
                LogUtil.info(SocketBase.FROM_ESP_STR + "有ESP连接到本机，IP地址：" + socket.getRemoteSocketAddress() + "端口号：" + socket.getPort());
                // 3、将socket传递给新线程
                ESPSocket cs = new ESPSocket(socket);
                ESPSocketManager.getInstance().setSocket(cs);
                cs.start();
                // 4、使用Chatmanager进行管理

                //开启监听器，用户接收到的处理数据。
                DataDealManager dataDealManager = new DataDealManager();
                dataDealManager.deal();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
