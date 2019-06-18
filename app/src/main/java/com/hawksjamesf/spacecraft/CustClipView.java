package com.hawksjamesf.spacecraft;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class CustClipView extends LinearLayout {
    public CustClipView(Context context) {
        this(context,null);
    }

    public CustClipView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public CustClipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,-1);
    }

    public CustClipView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        //与addView存在差异
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//        LayoutParams layoutParams = generateDefaultLayoutParams();
//        addView(view,layoutParams);
//        addView(view);
        View view = View.inflate(getContext(), R.layout.view_cust_clip, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }
}
