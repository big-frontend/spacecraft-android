package jamesfchen.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SearchEditText extends EditText {
    public SearchEditText(@NonNull Context context) {
        super(context);
    }

    public SearchEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static final int CHECK_LIMIT = 1000;

    public interface OnTextChangedListener {
        void onTextChanged(CharSequence text);
    }

    OnTextChangedListener l;

    public void setOnTextChangedListener(OnTextChangedListener l) {
        this.l = l;
    }
    CharSequence startText;
    Runnable check=new Runnable() {
        @Override
        public void run() {
            if (!equals(startText,getText())){
                startText = getText();
                l.onTextChanged(startText);
            }
        }
        public  boolean equals(final CharSequence s1, final CharSequence s2) {
            if (s1 == s2) return true;
            int length;
            if (s1 != null && s2 != null && (length = s1.length()) == s2.length()) {
                if (s1 instanceof String && s2 instanceof String) {
                    return s1.equals(s2);
                } else {
                    for (int i = 0; i < length; i++) {
                        if (s1.charAt(i) != s2.charAt(i)) return false;
                    }
                    return true;
                }
            }
            return false;
        }
    };


    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        removeCallbacks(check);
        postDelayed(check,CHECK_LIMIT);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(check);
    }
}
