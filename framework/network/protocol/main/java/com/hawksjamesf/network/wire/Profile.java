package com.hawksjamesf.network.wire;

import com.squareup.wire.FieldEncoding;
import com.squareup.wire.Message;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.ProtoReader;
import com.squareup.wire.ProtoWriter;
import com.squareup.wire.WireField;
import com.squareup.wire.internal.Internal;

import java.io.IOException;

import okio.ByteString;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawksjamesf
 * @since: Dec/16/2018  Sun
 */
public class Profile extends Message<Profile, Profile.Builder> {

    public static final ProtoAdapter<Profile> ADAPTER = new ProtoAdapter_Profile();
    private static final long serialVersionUID = 0L;
//    @WireField(tag = 1, adapter = "com.squareup.wire.ProtoAdapter#INT32" ,label = WireField.Label.REQUIRED)
    public final int id;
    @WireField(tag = 2, adapter = "com.squareup.wire.ProtoAdapter#STRING", label = WireField.Label.REQUIRED)
    public final String mobile;
//    @WireField(tag = 3, adapter = "com.squareup.wire.ProtoAdapter#STRING", label = WireField.Label.REQUIRED)
    public final String token;
//    @WireField(tag = 4, adapter = "com.squareup.wire.ProtoAdapter#STRING", label = WireField.Label.REQUIRED)
    public final String refreshToken;


    public Profile(final int id,
                      final String mobile,
                      final String token,
                      final String refreshToken,
                      ByteString unknownFields) {
        super(ADAPTER, unknownFields);
        this.id = id;
        this.mobile = mobile;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public Profile(final int id,
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

    public static final class ProtoAdapter_Profile extends ProtoAdapter<Profile> {

        ProtoAdapter_Profile() {
            super(FieldEncoding.LENGTH_DELIMITED, Profile.class);
        }

        @Override
        public int encodedSize(Profile value) {

            int sum = value.unknownFields().size();
            if (value.id != -1) {
                sum += ProtoAdapter.INT32.encodedSizeWithTag(1, value.id);
            }

            if (value.mobile != null) {
                sum += ProtoAdapter.STRING.encodedSizeWithTag(2, value.mobile);
            }

            if (value.token != null) {
                sum += ProtoAdapter.STRING.encodedSizeWithTag(3, value.token);
            }
            if (value.refreshToken != null) {
                sum += ProtoAdapter.STRING.encodedSizeWithTag(4, value.refreshToken);
            }
            return sum;
        }

        @Override
        public void encode(ProtoWriter writer, Profile value) throws IOException {
            if (value.id != -1) {
                ProtoAdapter.INT32.encodeWithTag(writer, 1, value.id);
            }

            if (value.mobile != null) {
                ProtoAdapter.STRING.encodeWithTag(writer, 2, value.mobile);
            }

            if (value.token != null) {
                ProtoAdapter.STRING.encodeWithTag(writer, 3, value.token);
            }
            if (value.refreshToken != null) {
                ProtoAdapter.STRING.encodeWithTag(writer, 4, value.refreshToken);
            }

            writer.writeBytes(value.unknownFields());
        }

        @Override
        public Profile decode(ProtoReader reader) throws IOException {
            Profile.Builder builder = new Profile.Builder();
            long mark = reader.beginMessage();
//        int tag;
//        while ((tag = reader.nextTag()) != -1) {
//            switch (tag) {
//                case 1:
//                    builder.setId(ProtoAdapter.INT32.decode(reader));
//                    break;
//                case 2:
//                    builder.setMobile(ProtoAdapter.STRING.decode(reader));
//                    break;
//                case 3:
//                    builder.setToken(ProtoAdapter.STRING.decode(reader));
//                    break;
//                case 4:
//                    builder.setRefreshToken(ProtoAdapter.STRING.decode(reader));
//                    break;
//                default:{
//                    FieldEncoding fieldEncoding = reader.peekFieldEncoding();
//                    Object value = fieldEncoding.rawProtoAdapter().decode(reader);
//                    builder.addUnknownField(tag, fieldEncoding, value);
//                }
//            }
//        }
            for (int tag; (tag = reader.nextTag()) != -1;) {
            }

            reader.endMessage(mark);
            return builder.build();
        }

        @Override
        public Profile redact(Profile value) {
            Profile.Builder builder = value.newBuilder();
            builder.clearUnknownFields();
            return builder.build();
        }
    }




    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", token='" + token + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof Profile)) return false;
        Profile o = (Profile) other;
        return Internal.equals(unknownFields(), o.unknownFields())
                && Internal.equals(id, o.id);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode;
        if (result == 0) {
            result = unknownFields().hashCode();
            result = result * 37 + (mobile != null ? mobile.hashCode() : 0);
            super.hashCode = result;
        }
        return result;
    }
}
