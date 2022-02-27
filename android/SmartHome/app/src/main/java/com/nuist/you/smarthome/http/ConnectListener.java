package com.nuist.you.smarthome.http;

import org.java_websocket.handshake.ServerHandshake;

/**
 * Created by youxuan
 */

public interface ConnectListener {

    void onOpen(ServerHandshake handshakedata, String url);
    void onMessage(String message);
    void onClose(final int code, final String reason, final boolean remote);
    void onError(final Exception ex);

}
