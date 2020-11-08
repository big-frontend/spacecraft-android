package retrofit2.callFactory;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.internal.http2.Header;
import okio.*;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

import javax.annotation.concurrent.GuardedBy;
import javax.net.ssl.HttpsURLConnection;

/**
 * Copyright ® $ 2020
 * All right reserved.
 */
public final class UrlConnectionCallFactory implements Call.Factory {
    /*
                String method = request.method();
                connection.setRequestMethod(method);
                for (int i = 0, size = request.headers().size(); i < size; ++i) {
                    String name = request.headers().name(i);
                    String value = request.headers().value(i);
                    connection.addRequestProperty(name, value);
                }
                if ("GET".equals(method)) {
                    connection.setDoInput(true);
                    connection.setInstanceFollowRedirects(false);
                } else if ("POST".equals(method)) {
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);
                    OutputStream outputStream = connection.getOutputStream();
                    Buffer buffer = new Buffer();
                    assert request.body() != null;
                    request.body().writeTo(buffer);
                    outputStream.write(buffer.readByteArray());
                    outputStream.flush();
                    outputStream.close();
                }
//                connection.setConnectTimeout(timeout);
//                connection.setReadTimeout(timeout);
//                connection.setHostnameVerifier();
//                connection.setSSLSocketFactory();
//                connection.setAllowUserInteraction();
//                connection.setChunkedStreamingMode();
//                connection.setDefaultUseCaches();
//                connection.setFixedLengthStreamingMode();
//                connection.setIfModifiedSince();
                connection.connect();
     */
    public static UrlConnectionCallFactory create() {
        return new UrlConnectionCallFactory();
    }

    @Override
    public <T> Call<T> newCall(@NotNull Request request, @NotNull Converter<ResponseBody, T> responseConverter) {
        return new UrlConnectionCall<>(request, responseConverter);
    }

    static final class UrlConnectionCall<T> implements Call<T> {
        private Request request;
        private HttpURLConnection rawConnection;
        private Converter<ResponseBody, T> responseConverter;
        @GuardedBy("this")
        private boolean executed;
        private volatile boolean canceled;

         UrlConnectionCall(
                Request request,
                Converter<ResponseBody, T> responseConverter) {
            this.request = request;
            this.responseConverter = responseConverter;
        }
        private HttpURLConnection createConnection() throws IOException {
            HttpURLConnection connection;

            if (request.url().isHttps()) {
                connection = (HttpsURLConnection) request.url().url().openConnection();
            } else {
                connection = (HttpURLConnection) request.url().url().openConnection();
            }
                String method = request.method();
                connection.setRequestMethod(method);
                for (int i = 0, size = request.headers().size(); i < size; ++i) {
                    String name = request.headers().name(i);
                    String value = request.headers().value(i);
                    connection.addRequestProperty(name, value);
                }
                if ("GET".equals(method)) {
                    connection.setDoInput(true);
                    connection.setInstanceFollowRedirects(false);
                } else if ("POST".equals(method)) {
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);

                }
//                connection.setConnectTimeout(timeout);
//                connection.setReadTimeout(timeout);
//                connection.setHostnameVerifier();
//                connection.setSSLSocketFactory();
//                connection.setAllowUserInteraction();
//                connection.setChunkedStreamingMode();
//                connection.setDefaultUseCaches();
//                connection.setFixedLengthStreamingMode();
//                connection.setIfModifiedSince();
            rawConnection = connection;
            return  connection;
        }
        @Override
        public Response<T> execute() throws IOException {
            HttpURLConnection connection;
            synchronized (this) {
                if (executed) throw new IllegalStateException("Already executed.");
                executed = true;
                connection = createConnection();
            }
            if (canceled) {
                connection.disconnect();
                throw new IOException("Canceled");
            }

            try {
                connection.connect();
                OutputStream outputStream = connection.getOutputStream();
                Buffer buffer = new Buffer();
                assert request.body() != null;
                request.body().writeTo(buffer);
                outputStream.write(buffer.readByteArray());
                outputStream.flush();
                outputStream.close();

                InputStream inputStream = connection.getInputStream();
                byte[] rawResponseBody = readInputStream(inputStream);
                int responseCode = connection.getResponseCode();
                for (Map.Entry<String, List<String>> entry:connection.getHeaderFields().entrySet()) {
                    System.out.println(entry.getKey() + ":"+ entry.getValue());
                }
                if (responseCode ==200){
                    String contentLength = connection.getHeaderField("Content-Length");
                    String contentType = connection.getHeaderField("Content-Type");
                    contentType= "application/json; charset=UTF-8";
                    ResponseBody responseBody = ResponseBody.create(MediaType.parse(contentType), rawResponseBody);
                    OkHttpCallFactory.OkHttpCall.ExceptionCatchingResponseBody catchingBody = new OkHttpCallFactory.OkHttpCall.ExceptionCatchingResponseBody(responseBody);
                    try {
                        T body = responseConverter.convert(catchingBody);
                        connection.disconnect();
                        return Response.success(body);
                    } catch (RuntimeException e) {
                        // If the underlying source threw an exception, propagate that rather than indicating it was
                        // a runtime exception.
                        catchingBody.throwIfCaught();
                        throw e;
                    }
//                    return  Response.success(responseBody)

                }

                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public static byte[] readInputStream(InputStream inputStream) {
            int count = 0;
            byte[] buff = new byte[4096];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                while ((count = inputStream.read(buff, 0, buff.length)) != -1) {
                    baos.write(buff, 0, count);
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return baos.toByteArray();
        }

        @Override
        public void enqueue(Callback<T> callback) {

        }

        @Override
        public boolean isExecuted() {
            return executed;
        }

        @Override
        public void cancel() {
            canceled = true;

            HttpURLConnection connection;
            synchronized (this) {
                connection = rawConnection;
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        @Override
        public boolean isCanceled() {
                return canceled;
        }

        @Override
        public Call<T> clone() {
            return new UrlConnectionCall<>(request, responseConverter);
        }

        @Override
        public Request request() {
            return request;
        }

        @Override
        public Timeout timeout() {
            return null;
        }


    }
}
