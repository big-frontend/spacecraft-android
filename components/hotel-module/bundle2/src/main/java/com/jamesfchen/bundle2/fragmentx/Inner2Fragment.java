package com.jamesfchen.bundle2.fragmentx;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.jamesfchen.bundle2.R;

public class Inner2Fragment extends Fragment {
    public static Inner2Fragment newInstance() {
        Inner2Fragment fragment = new Inner2Fragment();

        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inner, container, false);
        return rootView;
    }
}
