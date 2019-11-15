package com.hawksjamesf.uicomponent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class Inner1Fragment extends Fragment {
    public static Inner1Fragment newInstance() {
        Inner1Fragment fragment = new Inner1Fragment();

        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inner, container, false);
        return rootView;
    }
}
