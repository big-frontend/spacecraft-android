package com.hawksjamesf.common;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;

import java.util.List;
import java.util.function.Consumer;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Apr/06/2020  Mon
 */
public class BaseCustView2 extends View {
    private static final String basecustTag = "default";

    public BaseCustView2(Context context) {
        super(context);
        initView();
    }

    public BaseCustView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {

        setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener() {

            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                return insets;
            }
        });
        initListener();
    }
    private void initListener(){
        getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() {
            @Override
            public void onWindowAttached() {

            }

            @Override
            public void onWindowDetached() {

            }
        });
        getViewTreeObserver().addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
            @Override
            public void onWindowFocusChanged(boolean hasFocus) {

            }
        });
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

            }
        });
        getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {

            }
        });
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                return false;
            }
        });
        getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {

            }
        });
        getViewTreeObserver().addOnTouchModeChangeListener(new ViewTreeObserver.OnTouchModeChangeListener() {
            @Override
            public void onTouchModeChanged(boolean isInTouchMode) {

            }
        });
        getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {

            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getViewTreeObserver().addOnSystemGestureExclusionRectsChangedListener(new Consumer<List<Rect>>() {
                @Override
                public void accept(List<Rect> rects) {

                }
            });
        }

    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        return super.onApplyWindowInsets(insets);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    public void onWindowSystemUiVisibilityChanged(int visible) {
        super.onWindowSystemUiVisibilityChanged(visible);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


}
