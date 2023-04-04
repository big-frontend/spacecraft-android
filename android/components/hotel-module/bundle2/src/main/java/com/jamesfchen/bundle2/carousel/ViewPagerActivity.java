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
import com.jamesfchen.bundle2.databinding.ActivityViewpagerBinding;
import com.jamesfchen.bundle2.databinding.ItemDumpBinding;
import com.jamesfchen.util.ConvertUtil;

import jamesfchen.widget.carousel.CarouselView;
import jamesfchen.widget.kk.PagerView;
import jamesfchen.widget.carousel.CarouselPagerAdapter;
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
        ActivityViewpagerBinding binding = ActivityViewpagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Adapter adapter = new Adapter();
        binding.cv.setAdapter(adapter);
        binding.cv.setPageTransformer(new ZoomOutPageTransformer());
        adapter.setDataList(list);

        for (int i = 0; i < 4; i++) {

            ItemDumpBinding itemBinding = ItemDumpBinding.inflate(getLayoutInflater(),null,false);
            itemBinding.tvName.setText(String.valueOf(i));
            int position =i % 5;
            if (i == 0) {
                itemBinding.tvName.setBackgroundColor(Color.BLUE);
            } else if (position == 1) {
                itemBinding.tvName.setBackgroundColor(Color.DKGRAY);
            } else if (position == 2) {
                itemBinding.tvName.setBackgroundColor(Color.YELLOW);
            } else if (position == 3) {
                itemBinding.tvName.setBackgroundColor(Color.RED);
            } else if (position == 4) {
                itemBinding.tvName.setBackgroundColor(Color.CYAN);
            }
            binding.vfVf2.addView(itemBinding.getRoot(),i, new ViewGroup.LayoutParams(ConvertUtil.dp2px(240f),ConvertUtil.dp2px(100f)));
        }
        binding.vfVf2.hideAnimation(null);
        binding.vfVf2.setInAnimation(this, R.anim.anim_marquee_in);
        binding.vfVf2.setOutAnimation(this, R.anim.anim_marquee_out);
        binding.vfVf2.setAnimateFirstView(false);

        binding.cv.setOnClickListener(v -> {
            if (isFirst){
                binding.vfVf2.showAnimation(null);
                isFirst=false;
            }else {
                binding.vfVf2.hideAnimation(null);
                isFirst=true;

            }

        });
    }
    boolean isFirst=true;
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
