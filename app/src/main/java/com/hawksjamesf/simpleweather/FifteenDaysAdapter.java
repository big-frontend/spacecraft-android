package com.hawksjamesf.simpleweather;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hawksjamesf.simpleweather.bean.fifteendaysbean.SkyConBean;
import com.hawksjamesf.simpleweather.bean.fifteendaysbean.TempeBean;

import java.util.List;

import butterknife.ButterKnife;

import static android.view.View.inflate;

/**
 * Copyright ® 2017
 * Shanghai wind-mobi
 * All right reserved.
 *
 * @author:chenjinfa
 * @since:2017/6/29
 */


public class FifteenDaysAdapter extends RecyclerView.Adapter<FifteenDaysAdapter.FifteenDaysHolder> {
    private List<SkyConBean> mSkyConBeans;
    private List<TempeBean> mTempeBeans;
    private Activity mActivity;
    public FifteenDaysAdapter(Activity activity) {
        mActivity=activity;
    }

    @Override
    public FifteenDaysHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FifteenDaysHolder(inflate(mActivity, R.layout.item_fifteen_days_chart, null));
    }

    @Override
    public void onBindViewHolder(FifteenDaysHolder holder, int position) {
//        TempeBean tempeBean = mTempeBeans.get(position);
//        holder.mTtvDay.

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public void setData(List<TempeBean> tempeBeans, List<SkyConBean> skyconBeans) {
        mTempeBeans = tempeBeans;
        mSkyConBeans = skyconBeans;


    }

    static class FifteenDaysHolder extends RecyclerView.ViewHolder{
//        @BindView(R.id.tv_week)
//        TextView mTvWeek;
//        @BindView(R.id.tv_date)
//        TextView mTvDate;
//        @BindView(R.id.tv_day_weather)
//        TextView mTvDayWeather;
//        @BindView(R.id.iv_day_weather)
//        ImageView mIvDayWeather;
//        @BindView(R.id.ttv_day)
//        TemperatureView mTtvDay;
//        @BindView(R.id.iv_night_weather)
//        ImageView mIvNightWeather;
//        @BindView(R.id.tv_night_weather)
//        TextView mTvNightWeather;

        FifteenDaysHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
