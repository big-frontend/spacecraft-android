package com.jamesfchen.uicomponent.scrollExprimental;

import android.app.Activity;
import android.os.Bundle;

import com.jamesfchen.uicomponent.Adapter;
import com.jamesfchen.uicomponent.R;
import com.jamesfchen.uicomponent.ViewModel;

import java.util.ArrayList;
import java.util.List;

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
    public List<ViewModel> dataList = new ArrayList<ViewModel>() {
        {
            add(new ViewModel(R.drawable.tmp, "图片"));
            add(new ViewModel(R.drawable.baseline_3d_rotation_black_48, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.baseline_3d_rotation_black_48, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.baseline_3d_rotation_black_48, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.baseline_3d_rotation_black_48, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.baseline_3d_rotation_black_48, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
            add(new ViewModel(R.drawable.tmp, "你好吗我很好，她不好"));
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll);
        RecyclerView rv_image_text = findViewById(R.id.rv_image_text);
        Adapter adapter = new Adapter();
        rv_image_text.setAdapter(adapter);
        adapter.addDatas(dataList);

        RecyclerView rv_image_text_grid = findViewById(R.id.rv_image_text_grid);
        Adapter adapter2 = new Adapter();
        rv_image_text_grid.setAdapter(adapter2);
        adapter2.addDatas(new ArrayList<ViewModel>() {
            {
                add(new ViewModel(-1, "你好吗我很好，她不好"));
                add(new ViewModel(-1, "你好吗我很好，她不好"));
                add(new ViewModel(-1, "你好吗我很好，她不好"));
                add(new ViewModel(-1, "你好吗我很好，她不好"));
                add(new ViewModel(-1, "你好吗我很好，她不好"));
                add(new ViewModel(-1, "你好吗我很好，她不好"));
                add(new ViewModel(-1, "你好吗我很好，她不好"));
                add(new ViewModel(-1, "你好吗我很好，她不好"));
            }
        });

    }
}
