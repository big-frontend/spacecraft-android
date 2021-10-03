package jamesfchen.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.jamesfchen.common.util.ConvertUtil;

import org.jetbrains.annotations.NotNull;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: May/01/2019  Wed
 */
public class Divider extends RecyclerView.ItemDecoration {
    private Paint paint;
    private int space;

    public Divider(Context context) {
        this(context, 1);
    }

    public Divider(Context context, int heightDP) {
        space = ConvertUtil.dp2px(heightDP);
        paint = new Paint();
        paint.setColor(Color.WHITE);
    }

    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view, @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = space;
    }

    @Override
    public void onDraw(Canvas c, @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int space = ConvertUtil.dp2px(1);
        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);

            float top = view.getBottom();
            float bottom = view.getBottom() + space;
            c.drawRect(left, top, right, bottom, paint);

        }
    }
}
