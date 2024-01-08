package com.jamesfchen.bundle2.customview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jamesfchen.bundle2.R;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: May/29/2021  Sat
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView iv;
    TextView tv;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        iv = itemView.findViewById(R.id.iv);
        tv = itemView.findViewById(R.id.tv_text);
    }
}

