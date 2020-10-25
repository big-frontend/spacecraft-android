package com.hawksjamesf.uicomponent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class OuterFragment extends Fragment {

    public static OuterFragment newInstance() {
        OuterFragment fragment = new OuterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_outer, container, false);
    }

    SectionsPagerAdapterv2 mSectionsPagerAdapter;
    ViewPager mViewPager;
    RecyclerView rv;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = view.findViewById(R.id.vp_container);
//        mSectionsPagerAdapter = new SectionsPagerAdapterv2(getActivity().getSupportFragmentManager());
//        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setAdapter(new SectionsPagerAdapter(getParentFragmentManager()));
        rv = view.findViewById(R.id.rv);
        rv.setAdapter(new RecyclerViewAdapter(getParentFragmentManager()));
    }
}
