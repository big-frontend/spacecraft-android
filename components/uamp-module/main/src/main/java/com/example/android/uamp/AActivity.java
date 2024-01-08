package com.example.android.uamp;

import android.app.Activity;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.example.android.uamp.media.MusicService;

public class AActivity extends Activity {
    MediaBrowserCompat mediaBrowser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button bt = new Button(this);
        bt.setText("点击");
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaBrowser.isConnected()){
                    Log.d("cjf","已经连接");
                    return;
                }else {
                    mediaBrowser.connect();
                }

            }
        });
        setContentView(bt, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mediaBrowser = new MediaBrowserCompat(
                this,
                new ComponentName(this, MusicService.class),
                new MediaBrowserCompat.ConnectionCallback() {
                    @Override
                    public void onConnected() {
                        super.onConnected();
                        Log.d("cjf","onConnected AActivity");
                    }
                }, null
        );
    }
}
