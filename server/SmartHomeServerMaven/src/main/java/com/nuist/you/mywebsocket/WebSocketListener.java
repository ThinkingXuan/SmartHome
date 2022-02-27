package com.nuist.you.mywebsocket;

import com.nuist.you.util.LogUtil;
import com.nuist.you.util.SocketBase;

/**
 * author: youxuan
 */

public class WebSocketListener extends Thread {

    @Override
    public void run() {
        try {
            int port = SocketBase.PORT_SERVER_ANDROID;
            ChatServer server = new ChatServer(port);

            server.start();
            LogUtil.info("WebSocket服务器已开启，等待客户端接入，端口号: " + server.getPort());
            WebSocketManager.getInstance().setSocket(server);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
