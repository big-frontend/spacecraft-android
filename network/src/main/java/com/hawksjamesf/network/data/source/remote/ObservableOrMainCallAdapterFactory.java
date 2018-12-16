package com.hawksjamesf.network.data.source.remote;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/25/2018  Tue
 */
public class ObservableOrMainCallAdapterFactory extends CallAdapter.Factory {
    Scheduler scheduler;

    public ObservableOrMainCallAdapterFactory(Scheduler scheduler) {
        this.scheduler = scheduler;

    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != Observable.class) {

            return null;
        }

        final CallAdapter<Object, Observable<?>> delegate = (CallAdapter<Object, Observable<?>>) retrofit.nextCallAdapter(this, returnType, annotations);
        return new CallAdapter<Object, Object>() {
            @Override
            public Type responseType() {
                return delegate.responseType();
            }

            @Override
            public Object adapt(Call<Object> call) {
                Observable<?> adapt = delegate.adapt(call);
                return adapt.observeOn(scheduler);
            }
        };
    }
}
