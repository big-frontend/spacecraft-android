package com.jamesfchen.uicomponent.animationsExprimental;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.FloatArrayEvaluator;
import android.animation.FloatEvaluator;
import android.animation.IntArrayEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PointFEvaluator;
import android.animation.RectEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
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

import com.jamesfchen.common.util.ConvertUtil;
import com.jamesfchen.uicomponent.animationsExprimental.evaluators.ArgbEvaluator;
import com.jamesfchen.uicomponent.animationsExprimental.evaluators.HSVEvaluator;
import com.jamesfchen.uicomponent.animationsExprimental.evaluators.IntEvaluator;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jamesfchen.uicomponent.R;
/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/25/2018  Sun
 */
public class AnimActivity extends AppCompatActivity {

    //interpolators
    List<Interpolator> dataForInterpolators = new ArrayList<Interpolator>() {
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
    List<TypeEvaluator> dataForEvaluators = new ArrayList<TypeEvaluator>() {
        {
            add(new IntEvaluator());
            add(new FloatEvaluator());//translationX
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                add(new IntArrayEvaluator());
                add(new FloatArrayEvaluator());
                add(new PointFEvaluator());
            }

            add(new ArgbEvaluator());//backgroundColor
            add(new RectEvaluator());//clipBounds
            //custom
            add(new HSVEvaluator());
            //material
//            add(new MatrixEvaluator());//imageMatrix
//            add(new CircularRevealWidget.CircularRevealEvaluator());
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        //interpolators start
        final LinearLayout llInterpolator = findViewById(R.id.ll_interpolator);
        findViewById(R.id.bt_start_interpolator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0, size = dataForInterpolators.size(); i < size; ++i) {
                    TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0.7f,
                            Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f);
                    translateAnimation.setDuration(8000);
                    translateAnimation.setRepeatMode(Animation.RESTART);
                    translateAnimation.setRepeatCount(Animation.INFINITE);
                    TextView textView = (TextView) llInterpolator.getChildAt(i);
                    translateAnimation.setInterpolator((Interpolator) textView.getTag());
                    textView.startAnimation(translateAnimation);
                }
            }
        });
        for (Interpolator interpolator : dataForInterpolators) {
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
            llInterpolator.addView(textView, layoutParams);
        }
        //interpolators end

        //evaluators start
        final LinearLayout llEvaluators = findViewById(R.id.ll_evaluators);
        for (TypeEvaluator evaluator : dataForEvaluators) {
            View itemView = getLayoutInflater().inflate(R.layout.item_evaluator, llEvaluators, false);
            itemView.setTag(evaluator);
            llEvaluators.addView(itemView);
            TextView tvEvaluatorName = itemView.findViewById(R.id.tv_evaluator_name);
            ImageView ivEvaluator = itemView.findViewById(R.id.iv_evaluator);
            if (evaluator instanceof IntEvaluator) {

//                    }else if (evaluator instanceof IntArrayEvaluator){

            } else if (evaluator instanceof FloatEvaluator) {

//                    }else if (evaluator instanceof FloatArrayEvaluator){

//                    }else if (evaluator instanceof PointFEvaluator){

            } else if (evaluator instanceof ArgbEvaluator) {


            } else if (evaluator instanceof RectEvaluator) {
                ivEvaluator.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ivEvaluator.setImageResource(R.drawable.tmp);
                int width = ConvertUtil.dp2px(200f);
                int height = ConvertUtil.dp2px(200f);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        width, height);
                ivEvaluator.setLayoutParams(layoutParams);
            } else if (evaluator instanceof HSVEvaluator) {

            }
            tvEvaluatorName.setText(evaluator.getClass().getSimpleName());
        }
        findViewById(R.id.bt_start_evaluators).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0, size = dataForEvaluators.size(); i < size; ++i) {
                    LinearLayout childAt = (LinearLayout) llEvaluators.getChildAt(i);
                    TypeEvaluator evaluator = (TypeEvaluator) childAt.getTag();
                    ImageView ivEvaluator = childAt.findViewById(R.id.iv_evaluator);
                    ObjectAnimator anim = null;
                    String propertyName = "";
                    Object from = null;
                    Object to = null;
                    if (evaluator instanceof IntEvaluator) {
//                        Keyframe keyframe1 = Keyframe.ofFloat(0f, 0f);
//                        Keyframe keyframe2 = Keyframe.ofFloat(.5f, 200.0f);
//                        Keyframe keyframe3 = Keyframe.ofFloat(1f, 0f);
//
//                        PropertyValuesHolder property = PropertyValuesHolder.ofKeyframe("translationX", keyframe1, keyframe2, keyframe3);
//                        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(ivEvaluator,property);
//                        objectAnimator.start();
                        Animator animator = AnimatorInflater.loadAnimator(v.getContext(), R.animator.argb_evaluator);
                        animator.setTarget(ivEvaluator);
                        animator.start();

//                    }else if (evaluator instanceof IntArrayEvaluator){

                    } else if (evaluator instanceof FloatEvaluator) {
                        propertyName = "translationX";
                        from = 0f;
                        to = 200f;
//                    }else if (evaluator instanceof FloatArrayEvaluator){

//                    }else if (evaluator instanceof PointFEvaluator){

                    } else if (evaluator instanceof ArgbEvaluator) {
                        propertyName = "backgroundColor";
                        from = Color.RED;
                        to = Color.BLUE;

                    } else if (evaluator instanceof RectEvaluator) {
                        propertyName = "clipBounds";
                        Rect local = new Rect();
                        ivEvaluator.getLocalVisibleRect(local);
                        from = new Rect(local);
                        to = new Rect(local);

                        Rect tmpFrom = (Rect) from;
                        Rect tmpTo = (Rect) to;
                        tmpFrom.right = tmpFrom.left + local.width() / 4;
                        tmpFrom.bottom = tmpFrom.top + local.height() / 2;
                        tmpTo.left = tmpTo.right - local.width() / 4;
                        tmpTo.top = tmpTo.bottom - local.height() / 2;


                    } else if (evaluator instanceof HSVEvaluator) {
                        propertyName = "backgroundColor";
                        from = Color.RED;
                        to = Color.BLUE;
                    }
                    if (propertyName.length() != 0) {
                        anim = ObjectAnimator.ofObject(ivEvaluator,
                                propertyName,
                                evaluator,
                                from, to);
                        anim.setRepeatMode(ValueAnimator.REVERSE);
                        anim.setRepeatCount(ValueAnimator.INFINITE);
                        anim.setDuration(5000);
                        anim.start();
                    }
                }

            }
        });
        //evaluators end

//        final ValueAnimator animator = ValueAnimator.ofArgb(0,1);
//        final ValueAnimator valueAnimator;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            valueAnimator = ValueAnimator.ofArgb(0, 1);
////        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
////            @Override
////            public void onAnimationUpdate(ValueAnimator animation) {
////                iv.setAlpha((Float) animation.getAnimatedValue());
////            }
////        });
//            valueAnimator.setDuration(2000);
//            valueAnimator.setEvaluator(new ArgbEvaluator());
//        }

        /**
         * The following code snippet plays the following Animator objects in the following manner:
         1.Plays bounceAnim.
         2.Plays squashAnim1, squashAnim2, stretchAnim1, and stretchAnim2 at the same time.
         3.Plays bounceBackAnim.
         4.Plays fadeAnim.
         AnimatorSet bouncer = new AnimatorSet();
         bouncer.play(bounceAnim).before(squashAnim1);
         bouncer.play(squashAnim1).with(squashAnim2);
         bouncer.play(squashAnim1).with(stretchAnim1);
         bouncer.play(squashAnim1).with(stretchAnim2);
         bouncer.play(bounceBackAnim).after(stretchAnim2);
         ValueAnimator fadeAnim = ObjectAnimator.ofFloat(newBall, "alpha", 1f, 0f);
         fadeAnim.setDuration(250);
         AnimatorSet animatorSet = new AnimatorSet();
         animatorSet.play(bouncer).before(fadeAnim);
         animatorSet.start();
         */

    }
}
