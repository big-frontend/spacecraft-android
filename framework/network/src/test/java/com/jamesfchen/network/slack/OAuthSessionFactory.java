package com.jamesfchen.network.slack;


import java.io.Closeable;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.ByteString;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Jun/29/2019  Sat
 */
public class OAuthSessionFactory extends Dispatcher implements Closeable {
    SecureRandom secureRandom = new SecureRandom();
    private SlackApi slackApi;

    public OAuthSessionFactory(SlackApi slackApi) {
        this.slackApi = slackApi;
    }

    private MockWebServer mockWebServer;
    private Map<ByteString, Listener> listenerMap = new LinkedHashMap<>();

    public void start() throws Exception {
        if (mockWebServer != null) throw new IllegalStateException();
        mockWebServer = new MockWebServer();
        mockWebServer.setDispatcher(this);
        mockWebServer.start(53203);
    }

    public HttpUrl newAuthorizeUrl(String scopes, String team, Listener listener) {
        if (mockWebServer == null) throw new IllegalStateException();
        ByteString state = randomToken();
        synchronized (this) {
            listenerMap.put(state, listener);
        }

        return slackApi.authorizeUrl(scopes, redirectUrl(), state, team);
    }

    //http://localhost:53203/oauth/
    private HttpUrl redirectUrl() {
        return mockWebServer.url("/oauth/");
    }

    private ByteString randomToken() {
        byte[] bytes = new byte[16];
        secureRandom.nextBytes(bytes);
        return ByteString.of(bytes);
    }

    @Override
    public void close() throws IOException {
        if (mockWebServer == null) throw new IllegalStateException();
        mockWebServer.close();
    }

    @Override
    public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        HttpUrl requestUrl = mockWebServer.url(request.getPath());
        String code = requestUrl.queryParameter("code");
        String stateString = requestUrl.queryParameter("state");
        ByteString state = stateString != null ? ByteString.decodeBase64(stateString) : null;
        Listener listener;
        synchronized (this) {
            listener = listenerMap.get(state);
        }
        if (code == null || listener == null)
            return new MockResponse().setResponseCode(404).setBody("unexpected request");

        try {
            OAuthSession session = slackApi.exchangeCode(code, redirectUrl());
            listener.sessionGranted(session);
        } catch (IOException e) {
            e.printStackTrace();
            return new MockResponse()
                    .setResponseCode(400)
                    .setBody("code exchange failed: " + e.getMessage());
        }

        synchronized (this) {
            listenerMap.remove(state);
        }

        return new MockResponse()
                .setResponseCode(302)
                .setHeader("Location", "http://weibo.com/weizhistart");
    }

    public interface Listener {
        void sessionGranted(OAuthSession session);
    }
}
