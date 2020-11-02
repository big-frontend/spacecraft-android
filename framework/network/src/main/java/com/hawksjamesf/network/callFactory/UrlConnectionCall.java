package com.hawksjamesf.network.callFactory;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okio.Timeout;

final class UrlConnectionCall implements Call {
    public Request originalRequest;

    public UrlConnectionCall(Request originalRequest) {
        this.originalRequest = originalRequest;
    }

    @Override
    public void cancel() {

    }

    @NotNull
    @Override
    public Call clone() {
        return null;
    }

    @Override
    public void enqueue(@NotNull Callback callback) {

    }

    @NotNull
    @Override
    public Response execute() throws IOException {
        return null;
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public boolean isExecuted() {
        return false;
    }

    @NotNull
    @Override
    public Request request() {
        return null;
    }

    @NotNull
    @Override
    public Timeout timeout() {
        return null;
    }
}