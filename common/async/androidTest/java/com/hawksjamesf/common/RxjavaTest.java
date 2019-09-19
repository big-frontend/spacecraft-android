package com.hawksjamesf.common;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.runner.AndroidJUnit4;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RxjavaTest {
    OkHttpClient client;

    @Before
    public void setUp() {
        client = new OkHttpClient.Builder()
                .build();
    }

    @Test
    public void testRxjava() {
        Request request = new Request.Builder()
                .url(HttpUrl.parse("https://api.github.com/user"))
                .build();
        Call call = client.newCall(request);
        //map call to Completable Flowable Single Maybe Observable

        Observable.just(request)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Request, ObservableSource<Response>>() {
                    @Override
                    public ObservableSource<Response> apply(Request request) throws Exception {
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response response) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


//        try (Response resp = call.execute()) {
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
