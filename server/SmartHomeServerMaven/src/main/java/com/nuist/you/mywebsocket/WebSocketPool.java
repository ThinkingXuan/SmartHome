package com.nuist.you.mywebsocket;

import org.java_websocket.WebSocket;

import java.util.*;

/**
 * author: youxuan
 */

public class WebSocketPool {
    private static final Map<WebSocket, String> userconnections = new HashMap<WebSocket, String>();

    /**
     * 获取用户名 * @param session
     */
    public static String getUserByKey(WebSocket conn) {
        return userconnections.get(conn);
    }

    /**
     * 获取在线总数 * @param
     */
    public static int getUserCount() {
        return userconnections.size();
    }

    /**
     * 获取WebSocket * @param user
     */
    public static WebSocket getWebSocketByUser(String user) {
        Set<WebSocket> keySet = userconnections.keySet();
        synchronized (keySet) {
            for (WebSocket conn : keySet) {
                String cuser = userconnections.get(conn);
                if (cuser.equals(user)) {
                    return conn;
                }
            }
        }
        return null;
    }


    /**
     * 向连接池中添加连接 * @param inbound
     */
    public static boolean addUser(String user, WebSocket conn) {
        userconnections.put(conn, user); // 添加连接
        return true;
    }


    /**
     * 获取所有的在线用户 * @return
     */
    public static Collection<String> getOnlineUser() {
        List<String> setUsers = new ArrayList<String>();
        Collection<String> setUser = userconnections.values();
        for (String u : setUser) {
            setUsers.add(u);
        }
        return setUsers;
    }


    /**
     * 移除连接池中的连接 * @param inbound
     */
    public static boolean removeUser(WebSocket conn) {
        if (userconnections.containsKey(conn)) {
            userconnections.remove(conn); // 移除连接
            return true;
        } else {
            return false;
        }
    }


    /**
     * 向特定的用户发送数据 * @param user * @param message
     */
    public static void sendMessageToUser(WebSocket conn, String message) {
        if (null != conn) {
            conn.send(message);
        }
    }

    /**
     * 向所有的用户发送消息 * @param message
     */
    public static void sendMessage(String message) {

        uploadMessageToServer(message);
        Set<WebSocket> keySet = userconnections.keySet();
        synchronized (keySet) {
            for (WebSocket conn : keySet) {
                String user = userconnections.get(conn);
                if (user != null) {
                    conn.send(message);
                }
            }
        }
    }


    /**
     * 把聊天消息上传到服务器
     *
     * @param message
     */

    private static void uploadMessageToServer(String message) {

    }


}
