package com.hawksjamesf.network.callFactory;

import org.jetbrains.annotations.NotNull;

import okhttp3.Call;
import okhttp3.Request;

public class UrlConnectionCallFactory implements Call.Factory {
    @NotNull
    @Override
    public Call newCall(@NotNull Request request) {
        return new UrlConnectionCall(request);
    }

}
