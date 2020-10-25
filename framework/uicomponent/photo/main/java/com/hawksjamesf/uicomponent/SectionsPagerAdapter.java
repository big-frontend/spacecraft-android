package com.hawksjamesf.uicomponent;

import android.util.Log;

import com.hawksjamesf.uicomponent.model.Page;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/06/2018  Tue
 */

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    List<Page> dataList = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return PhotoFragment.newInstance(position,dataList.get(position));
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    public void setDataList(List<Page> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            this.dataList.clear();
            return;
        }
        Log.d(Constants.TAG_PHOTO_ACTIVITY,"page size:"+dataList.size());
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }


}