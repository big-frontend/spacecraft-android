package com.jamesfchen.network;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/25/2018  Tue
 */
//public class WeatherApiImpl implements WeatherApi {
//    BehaviorDelegate<WeatherApi> delegate;
//    Context context;
//
//    WeatherApiImpl(Context context, BehaviorDelegate<WeatherApi> delegate) {
//        this.delegate = delegate;
//        this.context = context;
//    }
//
//    @Override
//    public Single<WeatherData> getCurrentWeatherDate(String city) {
//        String file = null;
//        try {
//
//            file = ResourceUtils.readAssets2String("current_data.json");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        WeatherData data = new Gson().fromJson(file, WeatherData.class);
//        return delegate.returningResponse(data).getCurrentWeatherDate(city);
//    }
//
//    @Override
//    public Observable<ListRes<WeatherData>> getFiveData(String city) {
//        return null;
//    }
//}
