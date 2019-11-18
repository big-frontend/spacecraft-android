package com.hawksjamesf.uicomponent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/06/2018  Tue
 *
 */
public class SectionsPagerAdapterv2 extends FragmentPagerAdapter {

    public SectionsPagerAdapterv2(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return Inner1Fragment.newInstance();
    }

    @Override
    public int getCount() {
        return 1;
    }
}