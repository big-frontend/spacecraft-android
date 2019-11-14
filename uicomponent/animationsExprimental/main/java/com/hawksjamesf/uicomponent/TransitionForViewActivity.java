package com.hawksjamesf.uicomponent;

import android.app.Activity;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.google.firebase.perf.metrics.AddTrace;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/25/2018  Sun
 */
public class TransitionForViewActivity extends Activity {
    @AddTrace(name = "_transitionForViewActivity_onCreate", enabled = true /* optional */)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_for_view);
        SpanUtils.with(((TextView)findViewById(R.id.start)))
                .append("span utils")
                .setFontFamily("serif")
                .setBoldItalic()
//                .setFontSize(20)
                .setBlur(100, BlurMaskFilter.Blur.SOLID)
                .setLeadingMargin(10,20)
                .setBullet(Color.RED,10,3)
                .appendImage(R.mipmap.ic_launcher,SpanUtils.ALIGN_CENTER)
                .append("span utils2")
                .setForegroundColor(Color.BLUE)
                .setBackgroundColor(Color.GRAY)
                .appendImage(R.mipmap.ic_launcher,SpanUtils.ALIGN_CENTER)
                .appendImage(R.mipmap.ic_launcher,SpanUtils.ALIGN_CENTER)
                .appendImage(R.mipmap.ic_launcher,SpanUtils.ALIGN_CENTER)
                .appendImage(R.mipmap.ic_launcher,SpanUtils.ALIGN_CENTER)
                .appendImage(R.mipmap.ic_launcher,SpanUtils.ALIGN_CENTER)
                .appendImage(R.mipmap.ic_launcher,SpanUtils.ALIGN_CENTER)
                .appendImage(R.mipmap.ic_launcher,SpanUtils.ALIGN_CENTER)
                .append("span utils2")
                .appendSpace(ConvertUtils.dp2px(8))
                .appendLine("line")
                .appendImage(R.mipmap.ic_launcher,SpanUtils.ALIGN_CENTER)
                .appendLine()
                .append("span utils3")
                .setQuoteColor(Color.BLUE,100,30)
                .create();
    }
}
