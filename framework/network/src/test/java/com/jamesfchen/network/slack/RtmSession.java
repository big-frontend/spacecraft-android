package com.jamesfchen.network.slack;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.io.Closeable;
import java.io.IOException;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Jun/29/2019  Sat
 */
public class RtmSession extends WebSocketListener implements Closeable {
    private SlackApi slackApi;
    private WebSocket webSocket;

    public RtmSession(SlackApi slackApi) {
        this.slackApi = slackApi;
    }

    public void open(String botAccessToken) throws IOException {
        if (webSocket != null) throw new IllegalStateException();
        RtmConnectResponse rtmStartResponse = slackApi.rtmConnect(botAccessToken);
        webSocket = slackApi.rtm(rtmStartResponse.url, this);

    }

    @Override
    public void close() throws IOException {
        if (webSocket == null) return;
        WebSocket webscoket;
        synchronized (this) {
            webscoket = this.webSocket;
        }
        if (webscoket != null) {
            webscoket.close(1000, "bye");
        }
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        System.out.println("onOpen: " + response);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        System.out.println("onMessage: " +
                new GsonBuilder()
                        .setPrettyPrinting()
                        .create()
                        .toJson(new JsonParser().parse(text)));
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        super.onMessage(webSocket, bytes);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
        System.out.println("onClosing（ " + code + "): " + reason);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        System.out.println("onFailure: " + response);
    }

}
