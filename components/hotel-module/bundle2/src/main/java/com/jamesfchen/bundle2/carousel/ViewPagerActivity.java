package com.jamesfchen.bundle2.carousel;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jamesfchen.bundle2.R;
import jamesfchen.widget.carousel.CarouselView;
import jamesfchen.widget.carousel.PagerView;
import jamesfchen.widget.carousel.adapter.CarouselPagerAdapter;
import jamesfchen.widget.carousel.transformer.ZoomOutPageTransformer;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: jamesfchen
 * @email: jamesfchen@gmail.com
 * @since: Feb/16/2019  Sat
 */
public class ViewPagerActivity extends AppCompatActivity {
    List<Integer> list = new ArrayList<Integer>() {
        {
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
        }
    };

    CarouselView cv;
    VideoView vv;
    //    ChaplinVideoView clv;
    PagerView pv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        cv = findViewById(R.id.cv);
        Adapter adapter = new Adapter();
        cv.setAdapter(adapter);
        cv.setPageTransformer(new ZoomOutPageTransformer());
        adapter.setDataList(list);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vv.start();
//        clv.start();

    }

    public static class Adapter extends CarouselPagerAdapter<Integer> {
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
