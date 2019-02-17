package com.hawksjamesf.spacecraft.viewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hawksjamesf.common.CarouselView;
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
 * @author: jf.chen
 * @email: jf.chen@Ctrip.com
 * @since: Feb/16/2019  Sat
 */
public class ViewPagerActivity extends AppCompatActivity {
    List<Integer> list = new ArrayList<>();
    CarouselView cv;

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
        adapter.setDataList(list);

    }

    public class Adapter extends CarouselView.CarouselPagerAdapter {
        List<Integer> list = new ArrayList<>();

        public int getPagers() {
            return list.size();
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
            }else if (position ==4){
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


        void setDataList(@NonNull List<Integer> list) {
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();

        }
    }
}
