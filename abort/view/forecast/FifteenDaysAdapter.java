package com.hawksjamesf.spacecraft.ui.home.view.forecast;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hawksjamesf.spacecraft.R;
import com.hawksjamesf.spacecraft.data.bean.fifteendaysbean.SkyConBean;
import com.hawksjamesf.spacecraft.data.bean.fifteendaysbean.TempeBean;
import com.hawksjamesf.spacecraft.ui.home.view.forecast.older.TemperatureView;

import java.util.List;

/**
 * Copyright ® $ 2017
 * All right reserved.
 * Code Link : https://github.com/HawksJamesf/Spacecraft
 *
 * @author: hawks.jamesf
 * @since: 10/15/17
 */

public class FifteenDaysAdapter extends RecyclerView.Adapter<FifteenDaysAdapter.FifteenDaysHolder> {

//    private List<SkyConBean> mSCBeans;
//    private List<TempeBean> mTpBeans;
    private Activity mActivity;
    private LayoutInflater inflate;

    public FifteenDaysAdapter(Activity mActivity, List<TempeBean> mTpBeans, List<SkyConBean> mSCBeans) {
        this.mTpBeans = mTpBeans;
        this.mSCBeans = mSCBeans;
        this.mActivity = mActivity;
        inflate = LayoutInflater.from(mActivity);
    }

    @Override
    public FifteenDaysHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflate.inflate(R.layout.item_fifteen_days_chart, null);
        FifteenDaysHolder fifteenDaysHolder = new FifteenDaysHolder(view);
        fifteenDaysHolder.tvWeek = (TextView) view.findViewById(R.id.tv_week);
        fifteenDaysHolder.tvDate = (TextView) view.findViewById(R.id.tv_date);
        fifteenDaysHolder.tvDayWeather = (TextView) view.findViewById(R.id.tv_day_weather);
        fifteenDaysHolder.ivDayWeather = (ImageView) view.findViewById(R.id.iv_day_weather);
        fifteenDaysHolder.ttvDay = (TemperatureView) view.findViewById(R.id.ttv_day);
        fifteenDaysHolder.ivNightWeather = (ImageView) view.findViewById(R.id.iv_night_weather);
        fifteenDaysHolder.tvNightWeather = (TextView) view.findViewById(R.id.tv_night_weather);
        return fifteenDaysHolder;
    }

    @Override
    public void onBindViewHolder(FifteenDaysHolder holder, int position) {
//        if (mSCBeans == null || mTpBeans == null) return;

//        int fiveDaysMaxTempe = getFiveDaysMaxTempe(mTpBeans);
//        int fiveDayMinTempe = getFiveDayMinTempe(mTpBeans);
//        int fiveNightsMaxTempe = getFiveNightsMaxTempe(mTpBeans);
//        int fiveNightsMinTempe = getFiveNightsMinTempe(mTpBeans);
//
//        int max = fiveDaysMaxTempe > fiveNightsMaxTempe ? fiveDaysMaxTempe : fiveNightsMaxTempe;
//        int min = fiveDayMinTempe < fiveNightsMinTempe ? fiveDayMinTempe : fiveNightsMinTempe;
//        TempeBean tempeBean = mTpBeans.get(position);
//        holder.ttvDay.setMinTemp(min);
//        holder.ttvDay.setMaxTemp(max);
//        holder.ttvDay.setTemperatureDay((int) tempeBean.getMax());
//        holder.ttvDay.setTemperatureNight((int) tempeBean.getMin());
//
//
//        SkyConBean skyConBean = mSCBeans.get(position);
//        String condition = skyConBean.getValue();
////            Logger.d(condition);
//        String[] split = skyConBean.getDate().split("-");
//        String date = split[1] + "/" + split[2];
//
//
//        holder.ivDayWeather.setImageResource(ConditionUtils.getDayWeatherPic(condition));
//        holder.tvDayWeather.setText(condition);
//        holder.ivNightWeather.setImageResource(ConditionUtils.getNightWeatherPic(condition));
//        holder.tvNightWeather.setText(condition);
//        holder.tvDate.setText(date);
////            String curDate = new  SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        Calendar calendar = Calendar.getInstance();
//        try {
//            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(skyConBean.getDate()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        String[] weeks = mActivity.getResources().getStringArray(R.array.weeks);
//        holder.tvWeek.setText(weeks[calendar.get(Calendar.DAY_OF_WEEK) - 1]);


    }

    @Override
    public int getItemCount() {
        return mTpBeans.size();
    }

    static class FifteenDaysHolder extends RecyclerView.ViewHolder {
        TextView tvWeek;
        TextView tvDate;
        TextView tvDayWeather;
        ImageView ivDayWeather;
        TemperatureView ttvDay;
        ImageView ivNightWeather;
        TextView tvNightWeather;


        public FifteenDaysHolder(View itemView) {
            super(itemView);

        }
    }

//
//    private static class DayTempComparator implements Comparator<TempeBean> {
//
//        @Override
//        public int compare(TempeBean o1, TempeBean o2) {
//            if (o1.getMax() == o2.getMax()) {
//                return 0;
//            } else if (o1.getMax() > o2.getMax()) {
//                return 1;
//            } else {
//                return -1;
//            }
//        }
//    }
//
//    private static class NightTempComparator implements Comparator<TempeBean> {
//
//        @Override
//        public int compare(TempeBean o1, TempeBean o2) {
//            if (o1.getMin() == o2.getMin()) {
//                return 0;
//            } else if (o1.getMin() > o2.getMin()) {
//                return 1;
//            } else {
//                return -1;
//            }
//        }
//    }
//
//    private int getFiveDaysMaxTempe(List<TempeBean> tempeBeans) {
//        if (tempeBeans != null) {
//            return (int) Collections.max(tempeBeans, new DayTempComparator()).getMax();
//        }
//        return 0;
//    }
//
//    private int getFiveDayMinTempe(List<TempeBean> tempeBeans) {
//        if (tempeBeans != null) {
//            return (int) Collections.min(tempeBeans, new DayTempComparator()).getMax();
//        }
//        return 0;
//    }
//
//    private int getFiveNightsMaxTempe(List<TempeBean> tempeBeans) {
//        if (tempeBeans != null) {
//            return (int) Collections.max(tempeBeans, new NightTempComparator()).getMin();
//        }
//        return 0;
//    }
//
//    private int getFiveNightsMinTempe(List<TempeBean> tempeBeans) {
//        if (tempeBeans != null) {
//            return (int) Collections.min(tempeBeans, new NightTempComparator()).getMin();
//        }
//        return 0;
//    }

}
