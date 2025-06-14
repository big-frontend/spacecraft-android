package com.electrolytej.bundle2.page.customview.scrollExprimental;

import android.app.Activity;
import android.os.Bundle;

import com.electrolytej.bundle2.R;
import com.electrolytej.util.CollectionUtil;
import com.electrolytej.widget.recyclerview.ArrayAdapter;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: May/29/2021  Sat
 */
public class NestedScrollActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll);
        RecyclerView rv_image_text = findViewById(R.id.rv_image_text);
        ArrayAdapter<Object> adapter = new ArrayAdapter<>(
                R.layout.item_image_and_text,
                new int[]{R.id.tv_text, R.id.iv},
                CollectionUtil.list(
                        "图片1", com.electrolytej.base.R.drawable.tmp,
                        "图片2", com.electrolytej.base.R.drawable.tmp,
                        "图片3", com.electrolytej.base.R.drawable.tmp,
                        "图片4", com.electrolytej.base.R.drawable.tmp,
                        "图片5", com.electrolytej.base.R.drawable.tmp,
                        "图片6", com.electrolytej.base.R.drawable.tmp,
                        "图片7", com.electrolytej.base.R.drawable.tmp)
        );
        rv_image_text.setAdapter(adapter);

        RecyclerView rv_image_text_grid = findViewById(R.id.rv_image_text_grid);
        ArrayAdapter adapter2 = new ArrayAdapter(
                R.layout.item_image_and_text,
                new int[]{R.id.tv_text, R.id.iv},
                CollectionUtil.list(
                        "图片1", com.electrolytej.base.R.drawable.tmp,
                        "图片2", com.electrolytej.base.R.drawable.tmp,
                        "图片3", com.electrolytej.base.R.drawable.tmp,
                        "图片4", com.electrolytej.base.R.drawable.tmp,
                        "图片5", com.electrolytej.base.R.drawable.tmp,
                        "图片6", com.electrolytej.base.R.drawable.tmp,
                        "图片7", com.electrolytej.base.R.drawable.tmp)
        );
        rv_image_text_grid.setAdapter(adapter2);
    }
}
