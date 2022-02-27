package com.nuist.you.smarthome.http;

/**
 * Created by youxuan on 2017/8/6.
 *
 * sWebSocket服务器信息
 */

public class ServerInfo {

    private final String serverName;
    private final String serverAddress;

    public ServerInfo(String serverName, String serverAddress) {
        this.serverName = serverName;
        this.serverAddress = serverAddress;
    }

    @Override
    public String toString() {
        return serverName;
    }
}
