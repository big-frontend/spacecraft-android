package com.hawksjamesf.spacecraft;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.hawksjamesf.spacecraft.evaluators.HSVEvaluator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/25/2018  Sun
 */
public class AnimActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        final ImageView iv = findViewById(R.id.iv);
        ObjectAnimator anim = ObjectAnimator.ofObject(iv,
                "backgroundColor",
                new HSVEvaluator(),
                Color.RED, Color.BLUE);
        anim.setDuration(2000);
        anim.start();


        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                iv.setAlpha((Float) animation.getAnimatedValue());
            }
        });
        animator.start();
    }


}
