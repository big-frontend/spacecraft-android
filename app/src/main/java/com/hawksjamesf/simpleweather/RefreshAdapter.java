package com.hawksjamesf.simpleweather;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hawksjamesf.simpleweather.bean.SkyConBean;
import com.hawksjamesf.simpleweather.bean.TempeBean;

import java.util.List;

import butterknife.BindView;
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

public class RefreshAdapter extends BaseAdapter {
    @BindView(R.id.rv_fiften_days_forecast)
    FifteenDaysView mRvFiftenDaysForecast;
    private Activity mActivity;
    private List<SkyConBean> mSkyConBeans;
    private List<TempeBean> mTempeBeans;

    public RefreshAdapter(Activity activity, List<TempeBean> tempeBeans, List<SkyConBean> skyconBeans) {
        mActivity = activity;
        mTempeBeans = tempeBeans;
        mSkyConBeans = skyconBeans;
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
        View view = inflate(mActivity, R.layout.item_weather, null);
        ButterKnife.bind(this, view);
        mRvFiftenDaysForecast.setData(mTempeBeans);
//        mRvFiftenDaysForecast.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
//        FifteenDaysAdapter mFiftenDaysAdapter = new FifteenDaysAdapter(mActivity);
//        mRvFiftenDaysForecast.setAdapter(mFiftenDaysAdapter);

        return view;
    }


}
