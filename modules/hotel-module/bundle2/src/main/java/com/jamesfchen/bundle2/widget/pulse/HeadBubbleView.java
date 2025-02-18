package com.jamesfchen.bundle2.widget.pulse;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ConvertUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class HeadBubbleView extends FrameLayout {
    private final Context mContext;
    private final Point controlPointOne;
    private final Point controlPointTwo;
    private final int viewWidth;
    private final int viewHeight;
    private final int marginLeft;
    private final int marginBot;
    private final int height;
    private final float[] pos;
    private final float[] tan;
    //这个position很重要 不断的取出图片资源 靠它累加完成的
    private int position = 0;
    private List<BrowseEntity> browseEntities = new ArrayList<>();
    private List<ImageView> imageList = new ArrayList<>();
    private boolean isStop;
    private long riseDuration = 200;


    public HeadBubbleView(@NonNull Context context) {
        this(context, null);
    }

    public HeadBubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setFocusable(false);
        //三阶贝塞尔曲线控制点一
        controlPointOne = new Point();
        //三阶贝塞尔曲线控制点二
        controlPointTwo = new Point();
        //每个子view的宽高是固定的
        viewWidth = viewHeight = ConvertUtils.dp2px(22);
        marginLeft = ConvertUtils.dp2px(15);
        marginBot = ConvertUtils.dp2px(21);
        //父View的高度也是固定的
        height = ConvertUtils.dp2px(130);
        //用于从PathMeasure 中不断的取出 曲线的路径值
        pos = new float[2];
        tan = new float[2];
    }

    private boolean createAnimView() {
//        if (!isStop) {
//            return true;
//        }
        final ImageView imageView = imageList.get(position);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.setDuration(800);
        valueAnimator.addUpdateListener(animation -> {
            float animatedValue = (float) animation.getAnimatedValue();
            imageView.setScaleX(0.1f + animatedValue);
            imageView.setScaleY(0.1f + animatedValue);
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                //创建好后 设置缩放到最小
                imageView.setScaleX(0);
                imageView.setScaleY(0);
//                if (getChildCount()==1){
//                    stopAnimator();
//                    addView(imageList.get(0));
//                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (position == imageList.size() - 1) {
                    position = 0;
                } else {
                    position++;
                }
//                tempImageView.setBackgroundResource(browseEntity.drawableId);
                //动画执行完执行平移动画
                startTranslationAnimator(imageView);
            }
        });
        valueAnimator.start();
        return false;
    }

    private void startTranslationAnimator(final ImageView imageView) {
        Path path;
        int seed = (int) (Math.random() * 100);
        //根据随机数来确定是走左边曲线还是右边曲线
        if (seed % 2 == 0) {
            //曲线路径的封装
            path = createLeftPath();
        } else {
            //曲线路径的封装
            path = createLeftPath();
        }
        //通过PathMeasure 和ValueAnimator结合 在不同的阶段取出运动路径的x,y值
        final PathMeasure pathMeasure = new PathMeasure(path, false);
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimator.setDuration(riseDuration);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                int length = (int) (pathMeasure.getLength() * animatedValue);
                //在不同的阶段取出运动路径的x,y值
                pathMeasure.getPosTan(length, pos, tan);
                imageView.setTranslationX(pos[0]);
                imageView.setTranslationY(pos[1]);
                //同时做透明度动画
                imageView.setAlpha(animatedValue);
                if (animatedValue >= 0.5f) {
                    imageView.setScaleX(0.2f + animatedValue);
                    imageView.setScaleY(0.2f + animatedValue);
                }
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画执行完就移除View
                removeView(imageView);
//                if (getChildCount()==0){
//                    for (ImageView ivIcon : imageList) {
//                        addView(ivIcon);
//                    }
//                }

            }
        });
        valueAnimator.start();
    }

    private Path createRightPath() {
        return null;
    }

    private Path createLeftPath() {
        Path path = new Path();
        float nextFloat = new Random().nextFloat();
        path.moveTo(nextFloat, -height * 1.0f / 1.8f);
        //曲线控制点一
        controlPointOne.x = -(viewWidth);
        controlPointOne.y = -height / 5;
        //曲线控制点二
        controlPointTwo.x = -(viewWidth + marginLeft / 2);
        controlPointTwo.y = (int) (-height * 0.15);
        //生成三阶贝塞尔曲线
        path.cubicTo(controlPointOne.x, controlPointOne.y, controlPointTwo.x, controlPointTwo.y, 0, 0);
        return path;
    }

    Disposable subscribe;

    public void startAnimation(int innerDelay) {
        subscribe = Observable.interval(innerDelay, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (createAnimView()) return;
                    }
                });
    }

    //动画执行的一些开关操作
    public void stopAnimator() {
        isStop = false;
        if (null != subscribe) {
            subscribe.dispose();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimator();
    }

    public static class BrowseEntity {
        int drawableId;
        String name;

        public BrowseEntity(int drawableId, String name) {
            this.drawableId = drawableId;
            this.name = name;
        }
    }

    public void setDatas(List<BrowseEntity> browseEntities) {
        this.browseEntities.clear();
        this.imageList.clear();
        this.browseEntities.addAll(browseEntities);
        for (BrowseEntity browseEntity : browseEntities) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(browseEntity.drawableId);
            LayoutParams layoutParams = new LayoutParams(viewWidth, viewHeight);
            layoutParams.gravity = Gravity.BOTTOM | Gravity.END;
            layoutParams.setMargins(0, 0, marginLeft, marginBot);
            this.imageList.add(imageView);
            addView(imageView, layoutParams);
        }

    }

}
