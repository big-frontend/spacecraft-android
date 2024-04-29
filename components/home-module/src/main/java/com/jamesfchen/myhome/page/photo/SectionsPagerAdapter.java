package com.jamesfchen.myhome.page.photo;

import android.util.Log;

import com.jamesfchen.myhome.image.model.Photo;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/06/2018  Tue
 */

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    List<Photo> dataList = new ArrayList<>();

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

    public void setDataList(List<Photo> dataList) {
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