package com.nuist.you.espsocket;

/**
 * author: youxuan
 */

public class ESPSocketManager {
    private static ESPSocketManager cm;
    private ESPSocket cs;

    // 单例
    public static ESPSocketManager getInstance() {
        if (cm == null) {
            synchronized (ESPSocketManager.class) {
                if (cm == null) {
                    cm = new ESPSocketManager();
                }

            }
        }
        return cm;
    }


    // 添加ChatSocket
    public void setSocket(ESPSocket chatSocket) {
        cs = chatSocket;
    }

    public ESPSocket getSocket() {
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

    //发送消息给所有客户端
    public void publishAll(String out) {
        if (cs != null) {
            cs.out(out);
        }

    }
}
