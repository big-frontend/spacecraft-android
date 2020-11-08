package retrofit2.callFactory;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.RequestFactory;
import retrofit2.Response;

/**
 * Copyright ® $ 2020
 * All right reserved.
 *
 */
public final class VolleyCallFactory implements Call.Factory {
    public static VolleyCallFactory create() {
        return new VolleyCallFactory();
    }

    @Override
    public <T> Call<T> newCall(Request request, Converter<ResponseBody, T> responseConverter) {
        return new VolleyCall<>();
    }

    public static final class VolleyCall<T> implements Call<T> {
//        private RequestFactory requestFactory = null;
//        private Object[] args = null;
//        private okhttp3.Call.Factory callFactory = null;
//        private Converter<ResponseBody, T> responseConverter = null;

        VolleyCall(
                RequestFactory requestFactory,
                Object[] args,
                okhttp3.Call.Factory callFactory,
                Converter<ResponseBody, T> responseConverter) {
//            this.requestFactory = requestFactory;
//            this.args = args;
//            this.callFactory = callFactory;
//            this.responseConverter = responseConverter;
        }

        VolleyCall() {
        }

        @Override
        public Response<T> execute() throws IOException {
            return null;
        }

        @Override
        public void enqueue(Callback<T> callback) {

        }

        @Override
        public boolean isExecuted() {
            return false;
        }

        @Override
        public void cancel() {

        }

        @Override
        public boolean isCanceled() {
            return false;
        }

        @Override
        public Call<T> clone() {
            return null;
        }

        @Override
        public Request request() {
            return null;
        }

        @Override
        public Timeout timeout() {
            return null;
        }
    }
}
