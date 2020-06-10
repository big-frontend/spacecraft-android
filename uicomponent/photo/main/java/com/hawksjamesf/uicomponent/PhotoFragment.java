package com.hawksjamesf.uicomponent;

import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hawksjamesf.common.constants.MemoryUnit;
import com.hawksjamesf.common.util.MemoryUtil;
import com.hawksjamesf.uicomponent.model.Page;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_PAGE = "page";
    Page page;
    int sectionNumber = -1;
    ImageView ivPhoto;
    TextView tvText;

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
        ActivityManager am = ContextCompat.getSystemService(getActivity(), ActivityManager.class);
        final ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        Bundle arguments = getArguments();
        if (arguments == null) return;
        page = arguments.getParcelable(ARG_PAGE);
        sectionNumber = arguments.getInt(ARG_SECTION_NUMBER);
        ivPhoto = view.findViewById(R.id.iv_photo);
        tvText = view.findViewById(R.id.tv_text);

        if (page.uri == null) {
            tvText.setText(String.valueOf(sectionNumber));
            tvText.setVisibility(View.VISIBLE);
            ivPhoto.setVisibility(View.GONE);
        } else {
            tvText.setVisibility(View.GONE);
            ivPhoto.setVisibility(View.VISIBLE);
            Glide.with(ivPhoto.getContext())
                    .load(page.uri)
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            Bitmap bitmap = ImageUtils.drawable2Bitmap(resource);
                            long availableMegs = mi.availMem / MemoryUnit.MB;
                            long totalMegs = mi.totalMem / MemoryUnit.MB;
                            double  percentAvail = mi.availMem*100.0/mi.totalMem;
                            Log.d(Constants.TAG_PHOTO_ACTIVITY,"index:"+sectionNumber+" bitmap size:"+(bitmap.getByteCount()/1024f)+"k"+"\n" +
                                    "avaliable memory:"+availableMegs+"m total memory:"+totalMegs+"m percent:"+percentAvail+"%");
                            return false;
                        }
                    })
                    .into(ivPhoto);

        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
