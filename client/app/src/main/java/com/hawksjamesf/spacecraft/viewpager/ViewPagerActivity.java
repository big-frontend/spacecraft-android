package com.hawksjamesf.spacecraft.viewpager;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.hawksjamesf.common.CarouselView;
import com.hawksjamesf.common.ChaplinView;
import com.hawksjamesf.common.Constants;
import com.hawksjamesf.common.adapter.CarouselPagerAdapter;
import com.hawksjamesf.common.transformer.ZoomOutPageTransformer;
import com.hawksjamesf.spacecraft.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Feb/16/2019  Sat
 */
public class ViewPagerActivity extends AppCompatActivity {
    List<Integer> list = new ArrayList<>();
    CarouselView cv;
    VideoView vv;
    ChaplinView clv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        cv = findViewById(R.id.cv);
        Adapter adapter = new Adapter();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        cv.setAdapter(adapter);
        cv.setPageTransformer(new ZoomOutPageTransformer());
        adapter.setDataList(list);

        vv = findViewById(R.id.vv);
//        MediaController mediaController = new MediaController(this);
//        vv.setMediaController(mediaController);
        AssetManager assets = getResources().getAssets();
        vv.setVideoURI(Uri.parse(Constants.videoUrl));
        clv = findViewById(R.id.clv);
        clv.setURI(Uri.parse(Constants.videoUrl));
        vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clv.start();
            }
        });
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0, 0);
            }
        });

    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vv.start();
        clv.start();

    }

    public class Adapter extends CarouselPagerAdapter<Integer> {
        public int getPagers() {
            return dataList.size();
        }

        @Override
        protected Object instantiatePager(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            position %= 5;
            if (position == 0) {
                imageView.setBackgroundColor(Color.BLUE);
            } else if (position == 1) {
                imageView.setBackgroundColor(Color.BLACK);
            } else if (position == 2) {
                imageView.setBackgroundColor(Color.YELLOW);
            } else if (position == 3) {
                imageView.setBackgroundColor(Color.RED);
            } else if (position == 4) {
                imageView.setBackgroundColor(Color.CYAN);

            }
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
            container.addView(imageView);
            return imageView;
        }

        @Override
        protected void destroyPager(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
