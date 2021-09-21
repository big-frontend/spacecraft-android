package com.jamesfchen.myhome.splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public abstract class AbsPermissionsActivity extends AppCompatActivity {
    private final List<String> mPermissions = new ArrayList<String>() {
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                add(Manifest.permission.READ_PHONE_NUMBERS);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                add(Manifest.permission.ACCESS_MEDIA_LOCATION);
                add(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                add(Manifest.permission.FOREGROUND_SERVICE);
            }
            add(Manifest.permission.ACCESS_FINE_LOCATION);
            add(Manifest.permission.ACCESS_COARSE_LOCATION);
            add(Manifest.permission.READ_EXTERNAL_STORAGE);
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            add(Manifest.permission.READ_PHONE_STATE);
            add(Manifest.permission.INTERNET);
            add(Manifest.permission.ACCESS_NETWORK_STATE);
            add(Manifest.permission.ACCESS_WIFI_STATE);
            add(Manifest.permission.CHANGE_WIFI_MULTICAST_STATE);
            add(Manifest.permission.CHANGE_WIFI_STATE);
            add(Manifest.permission.BLUETOOTH);
            add(Manifest.permission.BLUETOOTH_ADMIN);/* Manifest.permission.BLUETOOTH_PRIVILEGED,*/
            add(Manifest.permission.RECEIVE_BOOT_COMPLETED);
            add(Manifest.permission.RECORD_AUDIO);
            add(Manifest.permission.CAMERA);
        }

    };
    private final List<String> mFailurePermissions = new ArrayList<String>();
    private int mPermissionRequestCount = 0;
    private static final int MAX_NUMBER_REQUEST_PERMISSIONS = 2;
    private static final int REQUEST_CODE_PERMISSIONS = 101;
    private static final String KEY_PERMISSIONS_REQUEST_COUNT = "KEY_PERMISSIONS_REQUEST_COUNT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPermissionRequestCount = savedInstanceState.getInt(KEY_PERMISSIONS_REQUEST_COUNT, 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestPermissionsIfNecessary();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_PERMISSIONS_REQUEST_COUNT, mPermissionRequestCount);
    }

    void requestPermissionsIfNecessary() {
        if (!hasPermission()) {
            if (mPermissionRequestCount < MAX_NUMBER_REQUEST_PERMISSIONS) {
                ++mPermissionRequestCount;
                requestPermission();
            } else {
                StringBuffer sb = new StringBuffer();
                for (String p : mFailurePermissions) {
                    sb.append(p);
                    sb.append('\n');
                }
                Toast.makeText(this, "need permission:" + sb, Toast.LENGTH_LONG).show();
            }
            onRequestPermissionsResult();
        } else {
            onRequestPermissionsResult();
        }
    }

    private boolean hasPermission() {
        boolean hasPermission = true;
        for (String permission : mPermissions) {
            boolean b = (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED);
            if (!b) {
                mFailurePermissions.add(permission);
            }
            hasPermission &= b;
        }
        return hasPermission;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int size = mPermissions.size();
            String[] a = new String[size];
            for (int i = 0; i < size; ++i) {
                a[i] = mPermissions.get(i);
            }
            requestPermissions(a, REQUEST_CODE_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE_PERMISSIONS == requestCode) {
            requestPermissionsIfNecessary();
        }
    }

    abstract protected void onRequestPermissionsResult();
}
