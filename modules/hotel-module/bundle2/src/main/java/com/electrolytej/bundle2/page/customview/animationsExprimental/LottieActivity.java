package com.electrolytej.bundle2.page.customview.animationsExprimental;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.electrolytej.bundle2.databinding.ActivityLottieBinding;
/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/24/2018  Sat
 */
public class LottieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLottieBinding binding = ActivityLottieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        lav.setAnimation("src/animationsExprimental/assets/lottie.json");
//        lav.playAnimation();
//        LottieCompositionFactory.fromJsonInputStream()
    }
}
