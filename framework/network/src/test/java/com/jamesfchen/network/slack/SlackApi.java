package com.jamesfchen.network.slack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Jun/27/2019  Thu
 */
public class SlackApi {
    private final HttpUrl baseUrl = HttpUrl.get("https://slack.com/api/");
    private final OkHttpClient httpClient;
    public final String clientId;
    public final String clientSecret;
    public final int port;
    private Gson gson;
    private Moshi moshi;

    public SlackApi(String clientId, String clientSecret, int port) {
        this.httpClient = new OkHttpClient.Builder()
                .build();
        gson = new GsonBuilder()
                .registerTypeAdapter(String.class, new GsonSerializerTypeAdapter())
                .registerTypeAdapter(HttpUrl.class, new GsonDeserializerTypeAdapter())
                .setPrettyPrinting()
                .create();
        moshi = new Moshi.Builder()
                .add(new MoshiTypeAdapter())
                .build();
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.port = port;
    }

    public HttpUrl authorizeUrl(String scopes, HttpUrl redirectUrl, ByteString state, String team) {
        HttpUrl.Builder builder = baseUrl.newBuilder("/oauth/authorize")
                .addQueryParameter("client_id", clientId)
                .addQueryParameter("scope", scopes)
                .addQueryParameter("redirect_uri", redirectUrl.toString())
                .addQueryParameter("state", state.base64());
        if (team != null && team.length() > 0) builder.addQueryParameter("team", team);
        return builder.build();
    }

    public OAuthSession exchangeCode(String code, HttpUrl redirectUrl) throws IOException {
        HttpUrl url = baseUrl.newBuilder("oauth.access")
                .addQueryParameter("client_id", clientId)
                .addQueryParameter("client_secret", clientSecret)
                .addQueryParameter("code", code)
                .addQueryParameter("redirect_uri", redirectUrl.toString())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = httpClient.newCall(request);
        try (Response response = call.execute()) {
            JsonAdapter<OAuthSession> jsonAdapter = moshi.adapter(OAuthSession.class);
            return jsonAdapter.fromJson(response.body().source());

        }
    }

    public RtmConnectResponse rtmConnect(String accessToken) throws IOException {
        HttpUrl ur = baseUrl.newBuilder("rtm.connect")
                .addQueryParameter("token", accessToken)
                .build();
        Request req = new Request.Builder()
                .url(ur)
                .build();
        Call call = httpClient.newCall(req);
        try (Response response = call.execute()) {
            JsonAdapter<RtmConnectResponse> jsonAdapter = moshi.adapter(RtmConnectResponse.class);
            RtmConnectResponse rtmConnectResponse = jsonAdapter.fromJson(response.body().source());
            System.out.println("rtm.connect response: " + rtmConnectResponse.toString());
            return rtmConnectResponse;
        }
    }

    public PostMessageResponse postMessageByEncodingJson(String accessToken, PostMessage postMessage) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl.newBuilder("chat.postMessage").build())
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .post(RequestBody.create(
                                MediaType.get("application/json; charset=utf-8"), moshi.adapter(PostMessage.class).toJson(postMessage)
                        ))
                .build();
        Call call = httpClient.newCall(request);
        try (Response response = call.execute()) {
            return moshi.adapter(PostMessageResponse.class).fromJson(response.body().source());
        }
    }

    public PostMessageResponse postMessageByEncodingUrl(String accessToken, Map<String, Object> map) throws IOException {
        if (map.isEmpty()
                || (isEmpty((String) map.get("token")) && isEmpty((String) map.get("channel")) && isEmpty((String) map.get("text")))
        ) return null;

        int size = map.size();
        int i = 1;
        StringBuilder args = new StringBuilder();
        for (String s : map.keySet()) {
            args.append(s);
            args.append("=");
            args.append(map.get(s));
            if (size != i) {
                args.append("&");
            }
            ++i;
        }
        Request request = new Request.Builder()
                .url(baseUrl.newBuilder("chat.postMessage").build())
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(RequestBody.create(MediaType.get("application/x-www-form-urlencoded"), args.toString()))
                .build();
        Call call = httpClient.newCall(request);
        try (Response response = call.execute()) {
            return moshi.adapter(PostMessageResponse.class).fromJson(response.body().source());
        }
    }

    public static final String start = "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; ";
    public static final String end = "------WebKitFormBoundary7MA4YWxkTrZu0gW--";

    public PostMessageResponse filesUploadByFormData(String accessToken, Map<String, Object> map) throws IOException {
        if (map.isEmpty() || (isEmpty((String) map.get("token")) && isEmpty((String) map.get("channel"))))
            return null;

        int size = map.size();
        int i = 1;
        StringBuilder args = new StringBuilder();
        for (String s : map.keySet()) {
            if (false) {
                args.append(start + "name=\"" + s + "\"; filename=\"" + map.get(s) + "\"\r\nContent-Type: image/png\r\n\r\n\r\n");
            } else {
                args.append(start + "name=\"" + s + "\"\r\n\r\n" + map.get(s) + "\r\n");

            }
            if (size == i) {
                args.append(end);
            }
            ++i;

        }
        Request request = new Request.Builder()
                .url(baseUrl.newBuilder("files.upload").build())
                .addHeader("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                .post(RequestBody.create(MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW"), args.toString()))
                .build();
        Call call = httpClient.newCall(request);
        try (Response response = call.execute()) {
            return moshi.adapter(PostMessageResponse.class).fromJson(response.body().source());
        }
    }

    boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public WebSocket rtm(HttpUrl url, WebSocketListener listener) {
        return httpClient.newWebSocket(new Request.Builder().url(url).build(), listener);
    }

    class MoshiTypeAdapter {
        @ToJson
        String urlToJson(HttpUrl httpUrl) {
            return httpUrl.toString();
        }

        @FromJson
        HttpUrl urlFromJson(String url) {
            if (url.startsWith("wss")) url = "https:" + url.substring(4);
            if (url.startsWith("ws")) url = "http:" + url.substring(3);
            return HttpUrl.get(url);
        }
    }

    class GsonSerializerTypeAdapter implements JsonSerializer<HttpUrl> {

        @Override
        public JsonElement serialize(HttpUrl src, Type typeOfSrc, JsonSerializationContext context) {
            return null;
        }
    }

    class GsonDeserializerTypeAdapter implements JsonDeserializer<String> {

        @Override
        public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return null;
        }
    }
}
