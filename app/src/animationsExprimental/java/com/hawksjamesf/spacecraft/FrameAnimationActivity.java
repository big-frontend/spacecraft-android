package com.hawksjamesf.spacecraft;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/25/2018  Sun
 */
public class FrameAnimationActivity extends AppCompatActivity {
    AnimationDrawable ad;
    AnimatedVectorDrawableCompat avd;
    boolean useVector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);
        ImageView imageView = new ImageView(this);
        if (useVector) {
            //foreground
            imageView.setImageResource(R.drawable.animation_vector_drawable_template);
            avd = (AnimatedVectorDrawableCompat) imageView.getDrawable();
        } else {
            //background
            imageView.setBackgroundResource(R.drawable.animation_drawable_template);
            ad = (AnimationDrawable) imageView.getBackground();
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ad.start();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //cannot be called during the onCreate()
        if (useVector) {
            avd.start();
        } else {
            ad.start();
        }
    }
}
