package com.hawksjamesf.network;

import com.squareup.wire.Message;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.WireField;

import okio.ByteString;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawksjamesf
 * @since: Dec/16/2018  Sun
 */
public class Profile extends Message<Profile, Profile.Builder> {

    private static final ProtoAdapter adapter = new ProtoAdapter_Profile();
    private static final long serialVersionUID = 0L;
    @WireField(tag = 1, adapter = "com.squareup.wire.ProtoAdapter#INT32", label = WireField.Label.REQUIRED)
    public final int id;
    @WireField(tag = 2, adapter = "com.squareup.wire.ProtoAdapter#STRING", label = WireField.Label.REQUIRED)
    public final String mobile;
    @WireField(tag = 3, adapter = "com.squareup.wire.ProtoAdapter#STRING", label = WireField.Label.REQUIRED)
    public final String token;
    @WireField(tag = 4, adapter = "com.squareup.wire.ProtoAdapter#STRING", label = WireField.Label.REQUIRED)
    public final String refreshToken;


    protected Profile(final int id,
                      final String mobile,
                      final String token,
                      final String refreshToken,
                      ByteString unknownFields) {
        super(adapter, unknownFields);
        this.id = id;
        this.mobile = mobile;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    protected Profile(final int id,
                      final String mobile,
                      final String token,
                      final String refreshToken) {
        this(id, mobile, token, refreshToken, ByteString.EMPTY);
    }

    @Override
    public Builder newBuilder() {
        Builder builder = new Builder()
                .setId(id)
                .setMobile(mobile)
                .setToken(token)
                .setRefreshToken(refreshToken);
        builder.addUnknownFields(unknownFields());
        return builder;
    }

    public static final class Builder extends Message.Builder<Profile, Builder> {
        private int id;
        private String mobile;
        private String token;
        private String refreshToken;

        public int getId() {
            return id;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public String getMobile() {
            return mobile;
        }

        public Builder setMobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public String getToken() {
            return token;
        }

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public Builder setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        @Override
        public Profile build() {
            return new Profile(id, mobile, token, refreshToken, buildUnknownFields());
        }
    }
}
