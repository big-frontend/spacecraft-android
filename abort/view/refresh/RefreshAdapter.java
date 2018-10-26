package com.hawksjamesf.simpleweather.ui.home.view.refresh;

import android.app.Activity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hawksjamesf.simpleweather.BuildConfig;
import com.hawksjamesf.simpleweather.R;
import com.hawksjamesf.simpleweather.data.bean.RealTimeBean;
import com.hawksjamesf.simpleweather.data.bean.fifteendaysbean.SkyConBean;
import com.hawksjamesf.simpleweather.data.bean.fifteendaysbean.TempeBean;
import com.hawksjamesf.simpleweather.ui.home.view.forecast.FifteenDaysAdapter;
import com.hawksjamesf.simpleweather.ui.home.view.forecast.FifteenDaysView;
import com.hawksjamesf.simpleweather.util.ConditionUtils;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.inflate;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/SimpleWeather
 *  @author: hawks jamesf
 *  @since: 2017/7/4
 */

public class RefreshAdapter extends BaseAdapter {

    private static final String TAG = "HomeFragment/RefreshAdapter";
    private Activity mActivity;
//    private List<SkyConBean> mSCBeans;
//    private List<TempeBean> mTpBeans;
    private RealTimeBean mRLBean;

    public RefreshAdapter(Activity activity) {
        mActivity = activity;

    }
//    public void setFifteenData(List<TempeBean> tempeBeans, List<SkyConBean> skyconBeans){
//        mTpBeans = tempeBeans;
//        mSCBeans = skyconBeans;
//    }
    public void setRealTimeData(RealTimeBean realTimeBean){
        mRLBean=realTimeBean;

    }


    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (BuildConfig.DEBUG){
            Logger.t(TAG).d(mRLBean+"\n"+mSCBeans+"\n"+mTpBeans+"\n");
        }

        View view = inflate(mActivity, R.layout.item_weather, null);
        if (mRLBean==null ||mSCBeans==null|| mTpBeans==null) return view;
        ViewHolder holder = new ViewHolder(view);
        //it is belong to older FifteenDaysView
//        holder.mRvFiftenDaysForecast.setData(mTpBeans, mSCBeans);

        //it is belog to new FifteenDaysView
        FifteenDaysAdapter fifteenDaysAdapter = new FifteenDaysAdapter(mActivity,mTpBeans,mSCBeans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.mRvFiftenDaysForecast.setLayoutManager(linearLayoutManager);
        holder.mRvFiftenDaysForecast.setAdapter(fifteenDaysAdapter);


        SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        String curDate = formater.format(new Date());
        try {
            calendar.setTime(formater.parse(curDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] weeks = mActivity.getResources().getStringArray(R.array.weeks);
        holder.mRealtimeWeek.setText(weeks[calendar.get(Calendar.DAY_OF_WEEK)-1]);
        holder.mRealtimeDate.setText(curDate);
        holder.mIvRealtimeCondition.setImageResource(ConditionUtils.getDayWeatherPic(mRLBean.getResult().getSkycon()));
        holder.mRealtimeTemperature.setText(String.valueOf(mRLBean.getResult().getTemperature()));
        return view;
    }



    static class ViewHolder {

        @BindView(R.id.rv_fiften_days_forecast)
        FifteenDaysView mRvFiftenDaysForecast;
        @BindView(R.id.realtime_date)
        TextView mRealtimeDate;
        @BindView(R.id.realtime_week)
        TextView mRealtimeWeek;
        @BindView(R.id.realtime_city)
        TextView mRealtimeCity;
        @BindView(R.id.iv_realtime_condition)
        ImageView mIvRealtimeCondition;
        @BindView(R.id.realtime_temperature)
        TextView mRealtimeTemperature;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }




}
