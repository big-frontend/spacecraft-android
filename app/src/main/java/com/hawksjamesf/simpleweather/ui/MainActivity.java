package com.hawksjamesf.simpleweather.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hawksjamesf.simpleweather.R;
import com.hawksjamesf.simpleweather.photo.PhotoActivity;
import com.hawksjamesf.simpleweather.util.ActivityUtil;
import com.hawksjamesf.simpleweather.video.VideoActivity;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import io.reactivex.disposables.Disposable;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends BaseActivity {

    private TextView mTextMessage;
    private Toolbar tb;
    private BottomNavigationView bnv;

    @Override
    protected void initComponent(@Nullable Bundle savedInstanceState) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        setContentView(R.layout.activity_main);
        tb = findViewById(R.id.tb);
        setSupportActionBar(tb);
        mTextMessage = findViewById(R.id.message);
        mTextMessage.setText("home");
        bnv = findViewById(R.id.bnv);

    }

    @Override
    protected void handleCallback(@NotNull Function1<? super Disposable, Unit> autoDisposable) {
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        mTextMessage.setText("home");
                        return true;
                    case R.id.navigation_dashboard:
                        mTextMessage.setText("dashboard");
                        return true;
                    case R.id.navigation_notifications:
                        mTextMessage.setText("notification");
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void loadData(@NotNull Function1<? super Disposable, Unit> autoDisposable) {

    }
}
