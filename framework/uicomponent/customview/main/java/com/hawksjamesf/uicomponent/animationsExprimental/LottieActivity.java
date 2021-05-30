package com.hawksjamesf.uicomponent.animationsExprimental;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.hawksjamesf.common.util.ConvertUtil;
import com.hawksjamesf.uicomponent.animationsExprimental.widget.ViewFlipper2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.hawksjamesf.uicomponent.R;
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
        setContentView(R.layout.activity_lottie);
        LottieAnimationView lav = findViewById(R.id.lav);
//        lav.setAnimation("src/animationsExprimental/assets/lottie.json");
//        lav.playAnimation();
//        LottieCompositionFactory.fromJsonInputStream()

        final ViewFlipper2 vf_vf2 = findViewById(R.id.vf_vf2);
        for (int i = 0; i < 4; i++) {
            View itemView = getLayoutInflater().inflate(R.layout.item_dump, null, false);
            TextView tv_name=((TextView) itemView.findViewById(R.id.tv_name));
            tv_name.setText(String.valueOf(i));
            int position =i % 5;
            if (i == 0) {
                itemView.setBackgroundColor(Color.BLUE);
            } else if (position == 1) {
                itemView.setBackgroundColor(Color.DKGRAY);
            } else if (position == 2) {
                itemView.setBackgroundColor(Color.YELLOW);
            } else if (position == 3) {
                itemView.setBackgroundColor(Color.RED);
            } else if (position == 4) {
                itemView.setBackgroundColor(Color.CYAN);
            }
            vf_vf2.addView(itemView,i, new ViewGroup.LayoutParams(ConvertUtil.dp2px(240f),ConvertUtil.dp2px(100f)));
        }

        vf_vf2.hideAnimation(null);
        vf_vf2.setInAnimation(this, R.anim.anim_marquee_in);
        vf_vf2.setOutAnimation(this, R.anim.anim_marquee_out);
        vf_vf2.setAnimateFirstView(false);
        lav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirst){
                    vf_vf2.showAnimation(null);
                    isFirst=false;
                }else {
                    vf_vf2.hideAnimation(null);
                    isFirst=true;

                }

            }
        });
    }
}
