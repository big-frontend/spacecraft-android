package com.hawksjamesf.network;

import com.squareup.wire.FieldEncoding;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.ProtoReader;
import com.squareup.wire.ProtoWriter;

import java.io.IOException;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawksjamesf
 * @since: Dec/16/2018  Sun
 */
public final class ProtoAdapter_Profile extends ProtoAdapter<Profile> {

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
        int tag;
        while ((tag = reader.nextTag()) != -1) {
            switch (tag) {
                case 1:
                    builder.setId(ProtoAdapter.INT32.decode(reader));
                    break;
                case 2:
                    builder.setMobile(ProtoAdapter.STRING.decode(reader));
                    break;
                case 3:
                    builder.setToken(ProtoAdapter.STRING.decode(reader));
                    break;
                case 4:
                    builder.setRefreshToken(ProtoAdapter.STRING.decode(reader));
                    break;
                default:
                    FieldEncoding fieldEncoding = reader.peekFieldEncoding();
                    Object value = fieldEncoding.rawProtoAdapter().decode(reader);
                    builder.addUnknownField(tag, fieldEncoding, value);
            }
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

