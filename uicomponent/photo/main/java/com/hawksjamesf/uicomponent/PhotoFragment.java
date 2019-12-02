package com.hawksjamesf.uicomponent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/06/2018  Tue
 * <p>
 * bitmap，drawable、uri、resource id
 */
public class PhotoFragment extends Fragment {
    public static final String TAG="PhotoFragment";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_PAGE = "page";
    private Page page;
    ImageView ivPhoto;

    public static PhotoFragment newInstance(int sectionNumber, Page page) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putParcelable(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments ==null) return;
        page = arguments.getParcelable(ARG_PAGE);
        ivPhoto= view.findViewById(R.id.iv_photo);
        ivPhoto.setImageBitmap(page.thumbnailBitmap);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
