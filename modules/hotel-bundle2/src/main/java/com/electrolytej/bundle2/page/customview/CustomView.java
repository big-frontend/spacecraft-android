package com.electrolytej.bundle2.page.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ConvertUtils;
/**
 * Copyright ® $ 2021
 * All right reserved.
 *
 * @author: jamesfchen
 * @email: hawksjamesf@gmail.com
 * @since: 五月/28/2021  星期五
 */
public class CustomView extends View {
    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("cjf","parent "+getParent().getClass().getName()+" CustomView onMeasure");
//        EXACTLY为match_parent or 200dp
        //AT_MOST 为wrap_content
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width=0,height=0;
        String wm,hm;
        switch (widthMode){
            case MeasureSpec.EXACTLY:{
                wm = "EXACTLY";
                width = widthSize;
                break;
            }
            case MeasureSpec.AT_MOST:{
                wm = "AT_MOST";
                break;
            }
            case MeasureSpec.UNSPECIFIED:{
                wm = "UNSPECIFIED";
                break;
            }
            default:
                wm = "default";
                break;
        }
        switch (heightMode){
            case MeasureSpec.EXACTLY:{
                hm = "EXACTLY";
                height = heightSize;
                break;
            }
            case MeasureSpec.AT_MOST:{
                hm = "AT_MOST";
                break;
            }
            case MeasureSpec.UNSPECIFIED:{
                hm = "UNSPECIFIED";
                break;
            }
            default:
                hm = "default";
                break;
        }
        Log.d("cjf","widthmode:"+wm+" "+ ConvertUtils.px2dp(widthSize) +" heightmode:"+hm+" "+ConvertUtils.px2dp(heightSize));
//        resolveSize()
//        setMeasuredDimension(width, height);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("cjf","parent "+getParent().getClass().getName()+" CustomView onDraw");
        super.onDraw(canvas);
    }
}
