package com.hawksjamesf.spacecraft.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

abstract class AbsPermissionsActivity extends AppCompatActivity {
    private String[] mPermissions = new String[]{
            Manifest.permission.ACCESS_MEDIA_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_MULTICAST_STATE,Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN,/* Manifest.permission.BLUETOOTH_PRIVILEGED,*/
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS,
            Manifest.permission.RECEIVE_BOOT_COMPLETED

    };
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
                Toast.makeText(this, "Go to Settings -> Apps and Notifications -> WorkManager Demo -> App Permissions\n" +
                        "        and grant access to Storage.", Toast.LENGTH_LONG).show();
            }
        } else {
            onRequestPermissionsResult();
        }
    }

    private boolean hasPermission() {
        boolean hasPermission = true;
        for (String permission : mPermissions) {
            boolean b = (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED);
            hasPermission &= b;
        }
        return hasPermission;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(mPermissions, REQUEST_CODE_PERMISSIONS);
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
