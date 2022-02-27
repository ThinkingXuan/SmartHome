package com.nuist.you.smarthome.http;

import android.util.Log;

import com.nuist.you.smarthome.base.HttpUrlBase;

import org.java_websocket.WebSocketImpl;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.drafts.Draft_75;
import org.java_websocket.drafts.Draft_76;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Created by youxuan
 * <p>
 * 处理webSocket的工具类
 */

public class MyWebSocket {
    private static final String TAG = "MyWebSocket";

    private WebSocketClient client;//连接客户端
    private DraftInfo selectDraft;// 连接协议

    private volatile static MyWebSocket myWebSocket;

    private DraftInfo[] draftInfos = {
            new DraftInfo("WebSocket协议Draft_17", new Draft_17()),
            new DraftInfo("webSocket协议Draft_10", new Draft_10()),
            new DraftInfo("WebSocket协议Draft_76", new Draft_76()),
            new DraftInfo("WebSocket协议Draft_75", new Draft_75())
    };//所有连接协议

    private String address;

    private MyWebSocket(){

    }



    public static MyWebSocket getInstance(){
        if (myWebSocket == null){
            synchronized (MyWebSocket.class){
                if (myWebSocket == null){
                    myWebSocket = new MyWebSocket();
                }
            }
        }
        return myWebSocket;

    }

    public void initSocket() {
        address = HttpUrlBase.SOCKET_URL;    //默认URL
        selectDraft = draftInfos[0];   //默认选择第一个连接协议
        ServerInfo[] serverInfos = {new ServerInfo("连接WebSocket后台", HttpUrlBase.SOCKET_URL)};// 所有连接后台
        WebSocketImpl.DEBUG = true;
        System.setProperty("java.net.preferIPv6Addresses", "false");
        System.setProperty("java.net.preferIPv4Stack", "true");

    }
    public void initSocket(String address){
        this.address = address;
        selectDraft = draftInfos[0];   //默认选择第一个连接协议
        WebSocketImpl.DEBUG = true;
        System.setProperty("java.net.preferIPv6Addresses", "false");
        System.setProperty("java.net.preferIPv4Stack", "true");
    }


    /**
     * 连接
     *
     * @param listener
     */
    public void socketConnect(final ConnectListener listener) {
        try {
            if (selectDraft == null) {
                return;
            }

            Log.d(TAG, "socketConnect: 连接地址" + address);
            client = new WebSocketClient(new URI(address), selectDraft.getDraft()) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    listener.onOpen(handshakedata, getURI().toString());

                }

                @Override
                public void onMessage(final String message) {
                    listener.onMessage(message);
                }

                @Override
                public void onClose(final int code, final String reason, final boolean remote) {
                    listener.onClose(code, reason, remote);
                }

                @Override
                public void onError(final Exception ex) {
                    listener.onError(ex);
                }
            };


            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭
     */
    public void socketClose() {
        if (client != null) {
            client.close();
        }
    }

    /**
     * 发送消息
     *
     * @param messageJson json格式的message
     */
    public void sendMessage(String messageJson) {
        try {
            if (client != null) {
                client.send(messageJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
