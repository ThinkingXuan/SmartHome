package com.nuist.you.espsocket;

import com.nuist.you.listener.DataReceiveListener;
import com.nuist.you.util.LogUtil;
import com.nuist.you.util.SocketBase;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * author: youxuan
 */

public class ESPSocket extends Thread {
    private Socket socket;

    private DataReceiveListener receiveListener;

    public ESPSocket(Socket socket) {
        this.socket = socket;
    }

    // 服务端传值给ESP8266
    public void out(String out) {
        try {
            if (socket.isConnected() && !socket.isClosed()) {
                // 获取当前Socket输出流，输出数据
                LogUtil.info(SocketBase.TO_ESP_STR + out);
                socket.getOutputStream().write(out.getBytes());
            } else {
                // 链接已关闭
                ESPSocketManager.getInstance().remove(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {
            if (socket.isConnected() && !socket.isClosed()) {

                //读取ESP发送给服务器的数据
                InputStream inputStream = socket.getInputStream();

                byte[] buffer = new byte[1024];
                int length;
                String str = "";
                while ((length = inputStream.read(buffer)) != -1) {
                    str = new String(buffer, 0, length);
                    LogUtil.info("服务器接收到ESP8266：" + str);
                    receiveListener.onReceiveESP8266(str);
                }
                inputStream.close();
//                byte[] msg = new byte[1024];
//                inputStream.read(msg);//读取流数据
//                inputStream.close();
////                socket.shutdownInput();
//
//                String str = new String(msg).trim();
//                LogUtil.info("服务器接收到ESP8266：" + str);
//                receiveListener.onReceiveESP8266(str);

            } else {
                // 链接已关闭
                ESPSocketManager.getInstance().remove(this);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setReceiveListener(DataReceiveListener receiveListener) {
        this.receiveListener = receiveListener;
    }

}
