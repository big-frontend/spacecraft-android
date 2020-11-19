package com.hawksjamesf.network.wire;


import org.junit.Before;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import okhttp3.mockwebserver.MockWebServer;
import okio.Buffer;
import okio.ByteString;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.wire.WireConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Jun/30/2019  Sun
 */
public class WireConverterFactoryTest {
    interface Service {
        @GET("/")
        Call<Profile> get();

        @POST("/")
        Call<Profile> post(
//                @Body Profile profile
        );
        @POST("/")
        Call<Phone> post2();

        @GET("/")
        Call<String> wrongClass();

        @GET("/")
        Call<List<String>> wrongType();
    }


    Service service;
    MockWebServer server = new MockWebServer();

    @Before
    public void setUp() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(server.url("/"))
                .addConverterFactory(WireConverterFactory.create())
                .build();

        service = retrofit.create(Service.class);
    }

//    @Test
//    public void serializeAndDeserialize() throws IOException {
//        Profile profile = new Profile.Builder()
//                .setId(2)
//                .setMobile("10086")
//                .setToken("1x2")
//                .setRefreshToken("1x3")
//                .build();
//
////        ByteString byteString = ByteString.decodeBase64("rO0ABXNyACdjb20uc3F1YXJldXAud2lyZS5NZXNzYWdlU2VyaWFsaXplZEZvcm0AAAAAAAAAAAIAAlsABWJ5dGVzdAACW0JMAAxtZXNzYWdlQ2xhc3N0ABFMamF2YS9sYW5nL0NsYXNzO3hwdXIAAltCrPMX+AYIVOACAAB4cAAAABMIAhIFMTAwODYaAzF4MiIDMXgzdnIAH2NvbS5oYXdrc2phbWVzZi5uZXR3b3JrLlByb2ZpbGUAAAAAAAAAAAIABEkAAmlkTAAGbW9iaWxldAASTGphdmEvbGFuZy9TdHJpbmc7TAAMcmVmcmVzaFRva2VucQB+AAdMAAV0b2tlbnEAfgAHeHIAGWNvbS5zcXVhcmV1cC53aXJlLk1lc3NhZ2UAAAAAAAAAAAIAAHhw");
////        Assertions.assertThat(serialize(profile)).isEqualTo(byteString);
//        ByteString encoded = ByteString.decodeBase64("Cg4oNTE5KSA4NjctNTMwOQ==");
//        server.enqueue(new MockResponse().setBody(new Buffer().write(encoded)));
//        server.enqueue(new MockResponse().setBody(new Buffer().write(serialize(profile))));
//
//
//        Call<Phone> call2 = service.post2();
//        Response<Phone> response2 = call2.execute();
//        Phone body2 = response2.body();
//        System.out.println(body2);//client response
//
//        Call<Profile> call = service.post();
//        Response<Profile> response = call.execute();
//        System.out.println(response.body());//client response
//    }

    public ByteString serialize(Profile profile) throws IOException {
        Buffer buffer = new Buffer();
        ObjectOutputStream stream = new ObjectOutputStream(buffer.outputStream());
        stream.writeObject(profile);
        return buffer.readByteString();
    }

    public Object deserialize(ByteString data) throws IOException, ClassNotFoundException {
        Buffer buffer = new Buffer().write(data);
        ObjectInputStream stream = new ObjectInputStream(buffer.inputStream());
        return stream.readObject();
    }
}
