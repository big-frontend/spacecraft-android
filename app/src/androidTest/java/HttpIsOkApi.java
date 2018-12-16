import android.content.Context;

import com.google.gson.Gson;
import com.hawksjamesf.spacecraft.data.bean.ListRes;
import com.hawksjamesf.spacecraft.data.bean.RealTimeBean;
import com.hawksjamesf.spacecraft.data.bean.home.WeatherData;
import com.hawksjamesf.network.data.source.remote.WeatherApi;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.mock.BehaviorDelegate;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawskjamesf
 * @since: Sep/25/2018  Tue
 */
public class HttpIsOkApi implements WeatherApi {
    BehaviorDelegate<WeatherApi> delegate;
    Context context;

    public HttpIsOkApi(Context context, BehaviorDelegate<WeatherApi> delegate) {
        this.delegate = delegate;
        this.context = context;
    }

    @Override
    public Call<RealTimeBean> getRealTimeData(String filename) {
        return null;
    }

    @Override
    public Observable<Response<WeatherData>> getCurrentWeatherDate(String city) {
        String file = null;
        try {
            file = RestServiceTestHelper.getStringFromFile(context, "current_data.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        WeatherData data = new Gson().fromJson(file, WeatherData.class);
        return delegate.returningResponse(data).getCurrentWeatherDate(city);
    }

    @Override
    public Observable<Response<ListRes<WeatherData>>> getFiveData(String city) {
        return null;
    }
}
