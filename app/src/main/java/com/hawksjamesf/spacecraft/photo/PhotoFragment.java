package com.hawksjamesf.spacecraft.photo;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hawksjamesf.spacecraft.R;

import androidx.fragment.app.Fragment;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/06/2018  Tue
 *
 * bitmap，drawable、uri、resource id
 */
public class PhotoFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Bitmap mBitmap;
    private Drawable mDrawable;
    private Uri uri;
    private int resourceId;

    public PhotoFragment() {
    }

    public static PhotoFragment newInstance(int sectionNumber) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo, container, false);
        return rootView;
    }
}
