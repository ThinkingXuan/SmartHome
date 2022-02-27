package com.nuist.you.mywebsocket;

import com.nuist.you.datamanager.DataDealManager;
import com.nuist.you.listener.DataReceiveListener;
import com.nuist.you.util.LogUtil;
import com.nuist.you.util.gsonutil.GsonUtil;
import com.nuist.you.util.gsonutil.Info;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * author: youxuan
 */

public class ChatServer extends WebSocketServer {


    private DataReceiveListener listener;


    public ChatServer(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }

    public ChatServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        //开启监听器，用户接收到的处理数据。
        DataDealManager dataDealManager = new DataDealManager();
        dataDealManager.deal();

        this.userjoin("[" + conn.getRemoteSocketAddress().getAddress().getHostAddress() + "]", conn);

        LogUtil.info(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " 进入房间 ！");

        //测试代码
//        String str = "你好\n";
//        ESPSocketManager.getInstance().publishAll(str);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        userLeave(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {


        listener.onReceiveAndroid(message);

        //Info info = GsonUtil.getObject(message).get(0);
        // System.out.println(info.toString());

//        if (message.toString() != null) {

//            WebSocketPool.sendMessage(message);

//            if (info.getObject().getContent().contains("@qq")) {
//                WebSocketPool.sendMessage("当前连接人数：" + WebSocketPool.getUserCount() + " \n"
//                        + "在线用户：" + WebSocketPool.getOnlineUser() + "\n"
//
//                );
//            }
        //       }

//        System.out.println("[" + conn.getRemoteSocketAddress().getAddress().getHostAddress() + "]" + message + "\n");

    }


    @Override
    public void onError(WebSocket conn, Exception e) {
        e.printStackTrace();

        if (conn != null) {
            conn.close();
        }
    }


    /**
     * 用户加入处理 * @param user
     */
    public void userjoin(String user, org.java_websocket.WebSocket conn) {


        boolean flag = WebSocketPool.addUser(user, conn); // 向连接池添加当前的连接对象
        if (flag) {                                       //发送增加用户消息。
            String perCount = WebSocketPool.getUserCount() + "";
            Info info = getInfo(2, perCount, null);
            WebSocketPool.sendMessage(GsonUtil.getJsonStr(info));
        }

    }

    /**
     * 用户下线处理 * @param user
     */
    public void userLeave(org.java_websocket.WebSocket conn) {
        String user = WebSocketPool.getUserByKey(conn);
        boolean b = WebSocketPool.removeUser(conn);
        // 在连接池中移除连接
        if (b) {

            String perCount = WebSocketPool.getUserCount() + "";
            Info info = getInfo(2, perCount, null);
            WebSocketPool.sendMessage(GsonUtil.getJsonStr(info));  //发送减少用户消息。
            String joinMsg = "[系统]" + user + "下线了";
            //    WebSocketPool.sendMessage(joinMsg);// 向在线用户发送当前用户退出的消息
            LogUtil.info(joinMsg);
        }
    }


    public Info getInfo(int code, String perCount, Info.DataBean bean) {
        Info info = new Info();

        info.setCode(code);
        info.setMessage(perCount);

        List<Info.DataBean> mlist = new ArrayList<>();
        if (bean != null) {
            mlist.add(bean);
            info.setObject(mlist);
        }

        return info;
    }


    public void setReceiveListener(DataReceiveListener listener) {
        this.listener = listener;
    }
    // 测试
//    public static void main(String[] args) throws InterruptedException, IOException {
//
//        int port = 8887;
//
//        ChatServer server = new ChatServer(port);
//
//        server.start();
//
//        System.out.println("房间已开启，等待客户端接入，端口号: " + server.getPort());
//
//    }
}
