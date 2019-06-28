package com.hawksjamesf.networkexprimental.slack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    private final String clientId;
    private final String clientSecret;
    private final int port;
    private Gson gson;

    public SlackApi(String clientId, String clientSecret, int port) {
        this.httpClient = new OkHttpClient.Builder()
                .build();
        gson = new GsonBuilder()
                .create();
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

            return null;
        }


    }
}
