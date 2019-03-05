package com.hawksjamesf.spacecraft;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hawksjamesf.common.util.ConvertUtil;

import java.util.ArrayList;
import java.util.List;

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

    //    RecyclerView rv;
    List<Interpolator> dataList = new ArrayList<Interpolator>() {
        {
            add(new LinearInterpolator());
            add(new AccelerateInterpolator());
            add(new DecelerateInterpolator());
            add(new AccelerateDecelerateInterpolator());

            add(new AnticipateInterpolator());
            add(new OvershootInterpolator());
            add(new AnticipateOvershootInterpolator());

            add(new BounceInterpolator());//弹球
            add(new CycleInterpolator(0.4f));//周期运动：sin(2 * PI * mCycles * x) mCycles周期的倍数
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        final ImageView iv = findViewById(R.id.iv);
        final LinearLayout ll = findViewById(R.id.ll);
//        ObjectAnimator anim = ObjectAnimator.ofObject(iv,
//                "backgroundColor",
//                new HSVEvaluator(),
//                Color.RED, Color.BLUE);
//        anim.setDuration(2000);
//        anim.start();
//
//
//        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                iv.setAlpha((Float) animation.getAnimatedValue());
//            }
//        });
//        animator.start();


//        rv = findViewById(R.id.rv);
//        final MyAdapter myAdapter = new MyAdapter(this);
//        rv.setAdapter(myAdapter);
//        rv.setLayoutManager(new LinearLayoutManager(rv.getContext(), RecyclerView.VERTICAL, false));
//        final int childCount = rv.getChildCount();

        findViewById(R.id.bt_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0, size = dataList.size(); i < size; ++i) {
                    TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0.7f,
                            Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f);
                    translateAnimation.setDuration(8000);
                    translateAnimation.setRepeatMode(Animation.RESTART);
                    translateAnimation.setRepeatCount(Animation.INFINITE);
                    TextView textView = (TextView) ll.getChildAt(i);
                    translateAnimation.setInterpolator((Interpolator) textView.getTag());
                    textView.startAnimation(translateAnimation);
                }
            }
        });
        for (Interpolator interpolator : dataList) {
            TextView textView = new TextView(this);
            textView.setBackgroundResource(R.color.apricot);
            textView.setText(
                    interpolator.getClass().getSimpleName().substring(0,
                            interpolator.getClass().getSimpleName().lastIndexOf("Interpolator")
                    )
            );
            textView.setGravity(Gravity.CENTER);
            textView.setTag(interpolator);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ConvertUtil.dp2px(100f), ConvertUtil.dp2px(50f));
            ll.addView(textView, layoutParams);
        }
    }

//
//    static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
//
//        Context context;
//        List<Interpolator> dataList = new ArrayList<Interpolator>() {
//            {
//                add(new LinearInterpolator());
//                add(new AccelerateInterpolator());
//                add(new DecelerateInterpolator());
//                add(new AccelerateDecelerateInterpolator());
//
//                add(new AnticipateInterpolator());
//                add(new OvershootInterpolator());
//                add(new AnticipateOvershootInterpolator());
//
//                add(new BounceInterpolator());//弹球
//                add(new CycleInterpolator(0.5f));//周期运动：sin(2 * PI * mCycles * x) mCycles周期的倍数
//            }
//        };
//
//        public MyAdapter(Context context) {
//            this.context = context;
//        }
//
//        @NonNull
//        @Override
//        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//            return new MyViewHolder(
//                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my, parent, false)
//            );
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//            holder.tvText.setText(
//                    dataList.get(position).getClass().getSimpleName()
//            );
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return dataList.size();
//        }
//
//    }
//
//    static class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView tvText;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tvText = itemView.findViewById(R.id.tv_text);
//        }
//    }
}
