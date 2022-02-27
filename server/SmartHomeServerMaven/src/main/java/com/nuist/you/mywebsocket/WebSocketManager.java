package com.nuist.you.mywebsocket;

import com.nuist.you.espsocket.ESPSocket;

/**
 * author: youxuan
 */

public class WebSocketManager {
    private static WebSocketManager cm;
    private ChatServer cs;

    // 单例
    public static WebSocketManager getInstance() {
        if (cm == null) {
            synchronized (WebSocketManager.class) {
                if (cm == null) {
                    cm = new WebSocketManager();
                }

            }
        }
        return cm;
    }


    // 添加ChatSocket
    public void setSocket(ChatServer chatSocket) {
        cs = chatSocket;
    }

    public ChatServer getSocket() {
        return cs;
    }

    // 移除ChatSocket
    public void remove(ESPSocket chatSocket) {
//        vector.remove(chatSocket);
    }

//    // 发送消息给指定客户端
//    public void publishOne(ESPSocket cs, String out) {
//        for (int i = 0; i < vector.size(); i++) {
//            ESPSocket chatSocket = (ESPSocket) vector.get(i);
//            if (!cs.equals(chatSocket)) {
//                chatSocket.out(out);
//            }
//        }
//    }

//    //发送消息给所有客户端
//    public void publishAll(String out) {
//
//
//        if (cs != null) {
//            cs.out(out);
//        }
//
//    }
}
