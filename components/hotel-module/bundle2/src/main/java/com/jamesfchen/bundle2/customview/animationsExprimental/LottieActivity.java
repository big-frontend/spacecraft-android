package com.jamesfchen.bundle2.customview.animationsExprimental;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jamesfchen.bundle2.R;
import com.jamesfchen.bundle2.databinding.ActivityLottieBinding;
import com.jamesfchen.bundle2.databinding.ItemDumpBinding;
import com.jamesfchen.common.util.ConvertUtil;
/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/24/2018  Sat
 */
public class LottieActivity extends AppCompatActivity {
    boolean isFirst=true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLottieBinding binding = ActivityLottieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        lav.setAnimation("src/animationsExprimental/assets/lottie.json");
//        lav.playAnimation();
//        LottieCompositionFactory.fromJsonInputStream()

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
        binding.lav.setOnClickListener(v -> {
            if (isFirst){
                binding.vfVf2.showAnimation(null);
                isFirst=false;
            }else {
                binding.vfVf2.hideAnimation(null);
                isFirst=true;

            }

        });
    }
}
