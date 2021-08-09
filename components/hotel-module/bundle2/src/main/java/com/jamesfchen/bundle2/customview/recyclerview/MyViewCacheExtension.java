package com.jamesfchen.bundle2.customview.recyclerview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewCacheExtension extends RecyclerView.ViewCacheExtension {
    @Nullable
    @Override
    public View getViewForPositionAndType(@NonNull RecyclerView.Recycler recycler, int position, int itemType) {
        return null;
    }
}
